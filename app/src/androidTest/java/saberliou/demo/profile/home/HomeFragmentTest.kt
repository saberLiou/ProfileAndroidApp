package saberliou.demo.profile.home

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import saberliou.demo.profile.GithubUser
import saberliou.demo.profile.MainActivity
import saberliou.demo.profile.ServiceLocator
import saberliou.demo.profile.data.source.FakeGithubRepository
import saberliou.demo.profile.data.source.IGithubRepository

@LargeTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class HomeFragmentTest {
    private lateinit var githubRepository: IGithubRepository
    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        githubRepository = FakeGithubRepository()
        ServiceLocator.githubRepository = githubRepository

        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        activityScenario.close()

        ServiceLocator.resetRepository()
    }

    @Test
    fun test01_areComponentsVisibleInFragment() = runBlockingTest {
        // GIVEN
        val githubUser = GithubUser(1, "saberLiou", "https://github.com/saberLiou", 6, 6)
        githubRepository.setGithubUser(githubUser)

        // WHEN
        launchFragmentInContainer<HomeFragment>()

        // THEN

    }
}
