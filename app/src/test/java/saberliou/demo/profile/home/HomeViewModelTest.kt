package saberliou.demo.profile.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import saberliou.demo.profile.GithubUser
import saberliou.demo.profile.data.source.FakeGithubRepository

@SmallTest
@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var githubRepository: FakeGithubRepository

//    @ExperimentalCoroutinesApi
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() = runBlockingTest {
        val githubUser = GithubUser(1, "saberLiou", "https://github.com/saberLiou", 6, 6)
        githubRepository = FakeGithubRepository().apply { setGithubUser(githubUser) }
        homeViewModel = HomeViewModel(githubRepository)
    }
}