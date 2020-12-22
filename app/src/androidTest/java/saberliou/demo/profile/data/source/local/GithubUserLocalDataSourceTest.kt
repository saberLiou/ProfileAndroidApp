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
import saberliou.demo.profile.GithubUser
import saberliou.demo.profile.MainCoroutineRule
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.data.source.GithubUserDataSource.Companion.GITHUB_USER_NOT_FOUND
import saberliou.demo.profile.getOrAwaitValue

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GithubUserLocalDataSourceTest {
    private lateinit var dataSource: GithubUserLocalDataSource

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var appDatabaseRule = AppDatabaseRule {
        dataSource = GithubUserLocalDataSource(it.githubUserDao(), Dispatchers.Main)
    }

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun createAndGetResult() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val expected = Result.Success(GithubUser())

        // WHEN
        dataSource.setGithubUser(expected.data)
        val actual = dataSource.getGithubUser()

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun observeResultAfterCreated() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val expected = Result.Success(GithubUser())

        // WHEN
        dataSource.setGithubUser(expected.data)
        val actual = dataSource.observeGithubUser().getOrAwaitValue()

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun clearResult() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val expected = Result.Error(Exception(GITHUB_USER_NOT_FOUND))
        dataSource.setGithubUser(GithubUser())

        // WHEN
        dataSource.clearGithubUser()

        // THEN
        val actual = dataSource.getGithubUser()
        assertEquals(expected, actual)
    }
}
