package saberliou.demo.profile.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import saberliou.demo.profile.util.MainCoroutineRule
import saberliou.demo.profile.SleepNight
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.data.source.SleepNightDataSource.Companion.SLEEP_NIGHT_NOT_FOUND
import saberliou.demo.profile.util.getOrAwaitValue

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SleepNightLocalDataSourceTest {
    private lateinit var dataSource: SleepNightLocalDataSource

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var appDatabaseRule = AppDatabaseRule {
        dataSource = SleepNightLocalDataSource(it.sleepNightDao(), Dispatchers.Main)
    }

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun createAndGetSpecificResult() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val expected = Result.Success(SleepNight(id = 1))

        // WHEN
        dataSource.createSleepNight(expected.data)
        val actual = dataSource.getSleepNight(expected.data.id)

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun createAndGetResult() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val expected = Result.Success(SleepNight())

        // WHEN
        dataSource.createSleepNight(expected.data)
        val actual = dataSource.getLatestSleepNight()

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun observeResultsAfterCreated() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val expected = Result.Success(
            arrayListOf(
                SleepNight(quality = 0),
                SleepNight(quality = 1),
                SleepNight(quality = 2)
            )
        )

        // WHEN
        expected.data.reversed().forEach {
            dataSource.createSleepNight(it)
        }
        val actual = dataSource.observeSleepNights().getOrAwaitValue()

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun updateResult() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        dataSource.createSleepNight(SleepNight())
        val expected = Result.Success(
            (dataSource.getLatestSleepNight() as Result.Success).data.copy(quality = 0)
        )

        // WHEN
        dataSource.updateSleepNight(expected.data)

        // THEN
        val actual = dataSource.getSleepNight(expected.data.id)
        assertEquals(expected, actual)
    }

    @Test
    fun deleteResults() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val expected = Result.Error(Exception(SLEEP_NIGHT_NOT_FOUND))
        dataSource.createSleepNight(SleepNight())

        // WHEN
        dataSource.deleteSleepNights()

        // THEN
        val actual = dataSource.getLatestSleepNight()
        assertEquals(expected, actual)
    }
}
