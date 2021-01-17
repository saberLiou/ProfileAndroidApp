package saberliou.demo.profile.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import saberliou.demo.profile.util.MainCoroutineRule
import saberliou.demo.profile.SleepNight
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.util.getOrAwaitValue

@SmallTest
@ExperimentalCoroutinesApi
class SleepNightRepositoryTest {
    private lateinit var sleepNightLocalDataSource: SleepNightDataSource

    private lateinit var repository: SleepNightRepository

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() = mainCoroutineRule.runBlockingTest {
        sleepNightLocalDataSource = FakeSleepNightDataSource()

        repository = SleepNightRepository(sleepNightLocalDataSource, Dispatchers.Main)
    }

    @Test
    fun createAndGetSpecificSleepNight_inLocalDataSource() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val expected = Result.Success(SleepNight(id = 1))

        // WHEN
        repository.createSleepNight(expected.data)
        val actual = repository.getSleepNight(expected.data.id)

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun createAndGetSleepNight_inLocalDataSource() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val expected = Result.Success(SleepNight())

        // WHEN
        repository.createSleepNight(expected.data)
        val actual = repository.getLatestSleepNight()

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun observeSleepNightsAfterCreated() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val expected = Result.Success(
            arrayListOf(
                SleepNight(quality = 0),
                SleepNight(quality = 1),
                SleepNight(quality = 2)
            )
        )

        // WHEN
        expected.data.forEach {
            repository.createSleepNight(it)
        }
        val actual = repository.observeSleepNights().getOrAwaitValue()

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun updateSleepNight() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        repository.createSleepNight(SleepNight())
        val expected = Result.Success(
            (repository.getLatestSleepNight() as Result.Success).data.copy(quality = 0)
        )

        // WHEN
        repository.updateSleepNight(expected.data)

        // THEN
        val actual = repository.getSleepNight(expected.data.id)
        assertEquals(expected, actual)
    }

    @Test
    fun deleteSleepNights() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val expected = Result.Error(Exception(SleepNightDataSource.SLEEP_NIGHT_NOT_FOUND))
        repository.createSleepNight(SleepNight())

        // WHEN
        repository.deleteSleepNights()

        // THEN
        val actual = repository.getLatestSleepNight()
        assertEquals(expected, actual)
    }
}
