package saberliou.demo.profile

import android.view.Gravity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions.navigateTo
import androidx.test.espresso.matcher.ViewMatchers
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
import saberliou.demo.profile.util.DataBindingIdlingResourceRule
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

        val githubUser = GithubUser(1, "saberLiou", "https://github.com/saberLiou", 6, 6)
        runBlocking { githubRepository.setGithubUser(githubUser) }

        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResourceRule.monitorActivity(activityScenario)
    }

    @After
    fun tearDown() {
        activityScenario.close()

//        ServiceLocator.resetRepository()
    }

    @Test
    fun test01_navigateToSettingsFragment() {
        // GIVEN
        Espresso.onView(ViewMatchers.withId(R.id.drawerLayout))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())

        // WHEN
        Espresso.onView(ViewMatchers.withId(R.id.settingsFragment))
            .perform(ViewActions.click())

        // THEN
        Espresso.onView(ViewMatchers.withId(R.id.fragmentSettings))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test02_navigateToGithubRepositoriesFragment() {
        // GIVEN
        Espresso.onView(ViewMatchers.withId(R.id.drawerLayout))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())

        // WHEN
        Espresso.onView(ViewMatchers.withId(R.id.githubRepositoriesFragment))
            .perform(ViewActions.click())

        // THEN
        Espresso.onView(ViewMatchers.withId(R.id.fragmentGithubRepositories))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test03_navigateToTodoNotesFragment() {
        // GIVEN
        Espresso.onView(ViewMatchers.withId(R.id.drawerLayout))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())

        // WHEN
        Espresso.onView(ViewMatchers.withId(R.id.todoNotesFragment))
            .perform(ViewActions.click())

        // THEN
        Espresso.onView(ViewMatchers.withId(R.id.fragmentTodoNotes))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test04_navigateToSleepNightsFragment() {
        // GIVEN
        Espresso.onView(ViewMatchers.withId(R.id.drawerLayout))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())

        // WHEN
        Espresso.onView(ViewMatchers.withId(R.id.navView))
            .perform(navigateTo(R.id.sleepNightsFragment))

        // THEN
        Espresso.onView(ViewMatchers.withId(R.id.fragmentSleepNights))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
