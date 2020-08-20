package saberliou.demo.profile

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @Rule
    var mainActivityTestResult = ActivityTestRule(MainActivity::class.java)

    @Test
    fun launchApp() {
        onView(withText(R.string.tv_home_greeting)).check(matches(isDisplayed()))
    }
}