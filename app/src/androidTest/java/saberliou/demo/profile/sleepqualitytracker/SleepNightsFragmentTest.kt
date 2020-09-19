package saberliou.demo.profile.sleepqualitytracker

import android.view.Gravity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions.open
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import saberliou.demo.profile.MainActivity
import saberliou.demo.profile.R

@LargeTest
@RunWith(AndroidJUnit4::class)
class SleepNightsFragmentTest {
    @Rule
    @JvmField
    var mainActivityTestResult = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.drawerLayout)).check(matches(isClosed(Gravity.LEFT))).perform(open())
        onView(withId(R.id.sleepNightsFragment)).perform(click())
    }

    @Test
    fun areComponentsVisibleInFragment() {
        onView(withId(R.id.rvSleepNights)).check(matches(isDisplayed()))
    }
}
