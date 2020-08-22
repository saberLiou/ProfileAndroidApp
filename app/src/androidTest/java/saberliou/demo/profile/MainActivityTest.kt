package saberliou.demo.profile

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import saberliou.demo.profile.ImageViewDrawableMatcher.withDrawable

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var mainActivityTestResult = ActivityTestRule(MainActivity::class.java)

    @Test
    fun launchApp() {
        onView(withId(R.id.ivDeveloperImage)).check(matches(withDrawable(R.drawable.head_photo)))
        onView(withId(R.id.tvDeveloperName)).check(matches(withText(R.string.mainActivity_tvDeveloperName_text)))
        onView(withId(R.id.tvDeveloperMotto)).check(matches(withText(R.string.mainActivity_tvDeveloperMotto_text)))
    }
}
