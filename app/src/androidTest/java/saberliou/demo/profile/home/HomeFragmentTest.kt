package saberliou.demo.profile.home

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import saberliou.demo.profile.GithubUser
import saberliou.demo.profile.MainActivity
import saberliou.demo.profile.R
import saberliou.demo.profile.data.source.IGithubRepository
import saberliou.demo.profile.di.GithubRepositoryModule
import saberliou.demo.profile.di.SleepNightRepositoryModule
import saberliou.demo.profile.util.DataBindingIdlingResourceRule
import saberliou.demo.profile.util.isResourceVisible
import saberliou.demo.profile.util.isResourceWithText
import javax.inject.Inject

@LargeTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(
    GithubRepositoryModule::class,
    SleepNightRepositoryModule::class
)
class HomeFragmentTest {
    private val githubUser = GithubUser(
        1,
        "saberLiou",
        "https://avatars1.githubusercontent.com/u/16037726",
        6,
        6
    )
//    private lateinit var githubRepository: IGithubRepository

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var targetContext: Context

    @Inject
    lateinit var githubRepository: IGithubRepository

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @get:Rule
    var dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
//        githubRepository = FakeGithubRepository()
//        ServiceLocator.githubRepository = githubRepository

        // GIVEN
        hiltRule.inject()
        runBlocking { githubRepository.setGithubUser(githubUser) }

        // WHEN
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResourceRule.monitorActivity(activityScenario)
    }

    @After
    fun tearDown() {
        activityScenario.close()

//        ServiceLocator.resetRepository()
    }

    @Test
    fun areComponentsVisibleInFragment() = runBlockingTest {
        // THEN
        isResourceVisible(R.id.ivGithubUserImage)
        isResourceWithText(R.id.tvGithubUserName, githubUser.name)
        isResourceWithText(
            R.id.tvGithubUserFollowers,
            targetContext.resources.getString(
                R.string.tvGithubUserFollowers_text,
                githubUser.followers
            )
        )
        isResourceWithText(
            R.id.tvGithubUserFollowing,
            targetContext.resources.getString(
                R.string.tvGithubUserFollowing_text,
                githubUser.following
            )
        )
    }
}
