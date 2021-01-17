package saberliou.demo.profile.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import saberliou.demo.profile.util.getOrAwaitValue

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SleepNightDaoTest {
    private lateinit var sleepNightDao: SleepNightDao

    @get:Rule
    var appDatabaseRule = AppDatabaseRule {
        sleepNightDao = it.sleepNightDao()
    }

    // Temporary solution for runBlockingTest: https://github.com/Kotlin/kotlinx.coroutines/issues/1204.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun createAndGetSpecificEntity() = runBlockingTest {
        // GIVEN
        val expected = SleepNightEntity(id = 1)

        // WHEN
        sleepNightDao.insert(expected)
        val actual = sleepNightDao.get(expected.id)

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun createAndGetEntity() = runBlockingTest {
        // GIVEN
        val expected = SleepNightEntity()

        // WHEN
        sleepNightDao.insert(expected)
        val actual = sleepNightDao.getLatest()

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun observeEntitiesAfterCreated() = runBlockingTest {
        // GIVEN
        val expected = arrayListOf(
            SleepNightEntity(quality = 0),
            SleepNightEntity(quality = 1),
            SleepNightEntity(quality = 2)
        )

        // WHEN
        expected.reversed().forEach {
            sleepNightDao.insert(it)
        }
        val actual = sleepNightDao.observeAllDescending().getOrAwaitValue()

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun updateEntity() = runBlockingTest {
        // GIVEN
        sleepNightDao.insert(SleepNightEntity())
        val expected = sleepNightDao.getLatest()!!.copy(quality = 0)

        // WHEN
        sleepNightDao.update(expected)

        // THEN
        val actual = sleepNightDao.get(expected.id)
        assertEquals(expected, actual)
    }

    @Test
    fun deleteEntity() = runBlockingTest {
        // GIVEN
        val expected = null
        sleepNightDao.insert(SleepNightEntity())

        // WHEN
        sleepNightDao.deleteAll()

        // THEN
        val actual = sleepNightDao.getLatest()
        assertEquals(expected, actual)
    }
}
