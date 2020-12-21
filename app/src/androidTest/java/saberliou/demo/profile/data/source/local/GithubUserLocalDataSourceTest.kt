package saberliou.demo.profile.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import saberliou.demo.profile.GithubUser
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.data.source.GithubUserDataSource.Companion.GITHUB_USER_NOT_FOUND
import saberliou.demo.profile.getOrAwaitValue

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GithubUserLocalDataSourceTest {
    private lateinit var dataSource: GithubUserLocalDataSource

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var appDatabaseRule = AppDatabaseRule {
//        dataSource = GithubUserLocalDataSource(it.githubUserDao, Dispatchers.Main)
        dataSource = GithubUserLocalDataSource(it.githubUserDao(), Dispatchers.Main)
    }

    // runBlocking used here because of https://github.com/Kotlin/kotlinx.coroutines/issues/1204
    // TODO replace with runBlockingTest once issue is resolved
    @Test
    fun createAndGetResult() = runBlocking {
        // GIVEN
        val expected = Result.Success(GithubUser())

        // WHEN
        dataSource.setGithubUser(expected.data)
        val actual = dataSource.getGithubUser()

        // THEN
        assertEquals(actual, expected)
    }

    @Test
    fun observeResultAfterCreated() = runBlocking {
        // GIVEN
        val expected = Result.Success(GithubUser())

        // WHEN
        dataSource.setGithubUser(expected.data)
        val actual = dataSource.observeGithubUser().getOrAwaitValue()

        // THEN
        assertEquals(actual, expected)
    }

    @Test
    fun clear() = runBlocking {
        // GIVEN
        val expected = Result.Error(Exception(GITHUB_USER_NOT_FOUND))
        dataSource.setGithubUser(GithubUser())

        // WHEN
        dataSource.clearGithubUser()

        // THEN
        val actual = dataSource.getGithubUser()
        assertEquals(actual, expected)
    }
}
