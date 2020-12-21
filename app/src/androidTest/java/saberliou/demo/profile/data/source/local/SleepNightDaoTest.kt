package saberliou.demo.profile.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SleepNightDaoTest {
    private lateinit var sleepNightDao: SleepNightDao

    @get:Rule
    var appDatabaseRule = AppDatabaseRule {
//        sleepNightDao = it.sleepNightDao
        sleepNightDao = it.sleepNightDao()
    }

    // Temporary solution for runBlockingTest: https://github.com/Kotlin/kotlinx.coroutines/issues/1204.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun test01_createAndGetSleepNightEntity() = runBlockingTest {
        // GIVEN
        val expected = SleepNightEntity()

        // WHEN
        sleepNightDao.insert(expected)
        val actual = sleepNightDao.get(1)

        // THEN
        assertTrue(actual == expected)
    }

    @Test
    fun test02_updateSleepNightEntity() = runBlockingTest {
        // GIVEN
        sleepNightDao.insert(SleepNightEntity())
        val expected = sleepNightDao.get(1)!!.copy(quality = 0)

        // WHEN
        sleepNightDao.update(expected)

        // THEN
        val actual = sleepNightDao.get(1)!!
        assertTrue(expected.id == actual.id && actual == expected)
    }
}
