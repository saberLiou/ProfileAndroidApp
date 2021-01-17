package saberliou.demo.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import saberliou.demo.profile.data.source.IGithubRepository
import saberliou.demo.profile.data.source.ISleepNightRepository
import saberliou.demo.profile.di.GithubRepositoryModule
import saberliou.demo.profile.di.SleepNightRepositoryModule
import saberliou.demo.profile.util.*
import javax.inject.Inject

@LargeTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@HiltAndroidTest
@UninstallModules(
    GithubRepositoryModule::class,
    SleepNightRepositoryModule::class
)
class NavigationTest {
    //    private lateinit var githubRepository: IGithubRepository

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var githubRepository: IGithubRepository

    @Inject
    lateinit var sleepNightRepository: ISleepNightRepository

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @get:Rule
    var dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
//        githubRepository = ServiceLocator.provideGithubRepository(getApplicationContext())
        hiltRule.inject()

        runBlocking {
            githubRepository.setGithubUser(
                GithubUser(
                    1,
                    "saberLiou",
                    "https://avatars1.githubusercontent.com/u/16037726",
                    6,
                    6
                )
            )
        }

        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResourceRule.monitorActivity(activityScenario)
    }

    @After
    fun tearDown() {
        activityScenario.close()

//        ServiceLocator.resetRepository()
    }

    @Test
    fun test01_fromSettingsFragmentNavigateBackToHomeFragment() {
        // GIVEN
        navigateToFragment(activityScenario, R.id.settingsFragment)
        isNavigationDrawerClosed()
        openNavigationDrawer()

        // WHEN
        clickResource(R.id.homeFragment)

        // THEN
        isResourceDisplaying(R.id.fragmentHome)
    }

    @Test
    fun test02_navigateToSettingsFragment() {
        // GIVEN
        isNavigationDrawerClosed()
        openNavigationDrawer()

        // WHEN
        clickResource(R.id.settingsFragment)

        // THEN
        isResourceDisplaying(R.id.fragmentSettings)
    }

    @Test
    fun test03_navigateToGithubRepositoriesFragment() {
        // GIVEN
        isNavigationDrawerClosed()
        openNavigationDrawer()

        // WHEN
        clickResource(R.id.githubRepositoriesFragment)

        // THEN
        isResourceDisplaying(R.id.fragmentGithubRepositories)
    }

    @Test
    fun test04_navigateToTodoNotesFragment() {
        // GIVEN
        isNavigationDrawerClosed()
        openNavigationDrawer()

        // WHEN
        clickResource(R.id.todoNotesFragment)

        // THEN
        isResourceDisplaying(R.id.fragmentTodoNotes)
    }

    @Test
    fun test05_navigateToSleepNightsFragment() {
        // GIVEN
        isNavigationDrawerClosed()
        openNavigationDrawer()

        // WHEN
        clickResource(R.id.sleepNightsFragment)

        // THEN
        isResourceDisplaying(R.id.fragmentSleepNights)
    }

    @Test
    fun test06_navigateToContactMeFragment() {
        // GIVEN
        isNavigationDrawerClosed()
        openNavigationDrawer()

        // WHEN
        clickResource(R.id.contactMeFragment)

        // THEN
        isResourceDisplaying(R.id.fragmentContactMe)
    }
}
