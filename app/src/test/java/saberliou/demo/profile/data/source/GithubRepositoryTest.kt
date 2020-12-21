package saberliou.demo.profile.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import saberliou.demo.profile.GithubUser
import saberliou.demo.profile.MainCoroutineRule
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.data.source.GithubUserDataSource.Companion.GITHUB_USER_NOT_FOUND
import saberliou.demo.profile.getOrAwaitValue

@ExperimentalCoroutinesApi
class GithubRepositoryTest {
    private val remoteGithubUser = GithubUser(1, "saberLiou", "https://github.com/saberLiou", 6, 6)
    private lateinit var githubUserRemoteDataSource: GithubUserDataSource
    private lateinit var githubUserLocalDataSource: GithubUserDataSource

    private lateinit var githubRepository: GithubRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() = mainCoroutineRule.runBlockingTest {
        githubUserRemoteDataSource = FakeGithubUserDataSource().apply {
            setGithubUser(remoteGithubUser)
        }
        githubUserLocalDataSource = FakeGithubUserDataSource()

        githubRepository = GithubRepository(
            githubUserRemoteDataSource,
            githubUserLocalDataSource,
            Dispatchers.Main
        )
    }

    @Test
    fun getGithubUser_fromRemoteDataSource() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val expected = Result.Success(remoteGithubUser)

        // WHEN
        val actual = githubRepository.getGithubUser(true)

        // THEN
        assertEquals(actual, expected)
    }

    @Test
    fun refreshGithubUser_inLocalDataSource() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val expected = Result.Success(remoteGithubUser)

        // WHEN
        githubRepository.refreshGithubUser()

        // THEN
        val actual = githubUserLocalDataSource.getGithubUser()
        assertEquals(actual, expected)
    }

    @Test
    fun observeGithubUser_inLocalDataSource() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val expected = Result.Success(remoteGithubUser)
        githubUserLocalDataSource.setGithubUser(expected.data)

        // WHEN
        val actual = githubRepository.observeGithubUser().getOrAwaitValue()

        // THEN
        assertEquals(actual, expected)
    }

    @Test
    fun clearGithubUser_inLocalDataSource() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val expected = Result.Error(Exception(GITHUB_USER_NOT_FOUND))
        githubUserLocalDataSource.setGithubUser(remoteGithubUser)

        // WHEN
        githubRepository.clearGithubUser()

        // THEN
        val actual = githubUserLocalDataSource.getGithubUser()
        assertEquals(actual, expected)
    }
}
