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
import saberliou.demo.profile.getOrAwaitValue

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GithubUserDaoTest {
    private lateinit var githubUserDao: GithubUserDao

    // Temporary solution for runBlockingTest: https://github.com/Kotlin/kotlinx.coroutines/issues/1204.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var appDatabaseRule = AppDatabaseRule {
//        githubUserDao = it.githubUserDao
        githubUserDao = it.githubUserDao()
    }

    @Test
    fun createAndGetEntity() = runBlockingTest {
        // GIVEN
        val expected = GithubUserEntity()

        // WHEN
        githubUserDao.insert(expected)
        val actual = githubUserDao.getLatest()

        // THEN
        assertEquals(actual, expected)
    }

    @Test
    fun observeEntityAfterCreated() = runBlockingTest {
        // GIVEN
        val expected = GithubUserEntity()

        // WHEN
        githubUserDao.insert(expected)
        val actual = githubUserDao.observeLatest().getOrAwaitValue()

        // THEN
        assertEquals(actual, expected)
    }

    @Test
    fun deleteEntity() = runBlockingTest {
        // GIVEN
        val expected = null
        githubUserDao.insert(GithubUserEntity())

        // WHEN
        githubUserDao.deleteAll()

        // THEN
        val actual = githubUserDao.getLatest()
        assertEquals(actual, expected)
    }
}
