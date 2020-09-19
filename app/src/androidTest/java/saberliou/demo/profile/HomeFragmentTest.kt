package saberliou.demo.profile

import android.content.pm.ActivityInfo
import android.view.Gravity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions.open
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import saberliou.demo.profile.home.Developer
import saberliou.demo.profile.home.HomeFragment

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
    @Rule
    @JvmField
    var mainActivityTestResult = ActivityTestRule(MainActivity::class.java)

    @Test
    fun activateFragment() {
        val developer = Developer()
        onView(withId(R.id.ivDeveloperImage)).check(matches(ImageViewDrawableMatcher.withDrawable(R.drawable.head_photo)))

        // Developer's name
        onView(withId(R.id.tvDeveloperName)).check(matches(withText(developer.name)))
        onView(withId(R.id.ibtnDeveloperEditName)).check(matches(ImageViewDrawableMatcher.withDrawable(R.drawable.edit)))

        // Developer's motto
        onView(withId(R.id.tvDeveloperMotto)).check(matches(withText(developer.motto)))
        onView(withId(R.id.ibtnDeveloperEditMotto)).check(matches(ImageViewDrawableMatcher.withDrawable(R.drawable.edit)))
    }

    @Test
    fun updateDeveloperName() {
        val updatedName = "Guo-Xun Liu"
        onView(withId(R.id.ibtnDeveloperEditName)).perform(click())
        onView(withText(R.string.mdEditDeveloperNameTitle_text)).check(matches(isDisplayed()))
        onView(withId(R.id.md_input_message)).perform(closeSoftKeyboard())  // To prevent zh soft keyboard influence the positive button of Material Dialogs.
        onView(withText(R.string.mdEditDeveloperButton_text)).perform(click())
        onView(withText(R.string.mdEditDeveloperNameTitle_text)).check(matches(isDisplayed()))

        onView(withId(R.id.md_input_message)).perform(click(), replaceText(updatedName))
        onView(withText(R.string.mdEditDeveloperButton_text)).perform(click())
        onView(withText(R.string.mdEditDeveloperNameTitle_text)).check(doesNotExist())
        onView(withId(R.id.tvDeveloperName)).check(matches(withText(updatedName)))

        onView(
            withText(
                HomeFragment.makeToastString(
                    HomeFragment.Companion.UpdateTypes.NAME.getType(),
                    updatedName
                )
            )
        ).inRoot(ToastMatcher.withToast()).check(matches(isDisplayed()))
    }

    @Test
    fun updateDeveloperMotto() {
        val updatedMotto = "No codes, no life."
        onView(withId(R.id.ibtnDeveloperEditMotto)).perform(click())
        onView(withText(R.string.mdEditDeveloperMottoTitle_text)).check(matches(isDisplayed()))
        onView(withId(R.id.md_input_message)).perform(closeSoftKeyboard())  // To prevent zh soft keyboard influence the positive button of Material Dialogs.
        onView(withText(R.string.mdEditDeveloperButton_text)).perform(click())
        onView(withText(R.string.mdEditDeveloperMottoTitle_text)).check(matches(isDisplayed()))

        onView(withId(R.id.md_input_message)).perform(replaceText(updatedMotto))
        onView(withText(R.string.mdEditDeveloperButton_text)).perform(click())
        onView(withText(R.string.mdEditDeveloperMottoTitle_text)).check(doesNotExist())
        onView(withId(R.id.tvDeveloperMotto)).check(matches(withText(updatedMotto)))

        onView(
            withText(
                HomeFragment.makeToastString(
                    HomeFragment.Companion.UpdateTypes.MOTTO.getType(),
                    updatedMotto
                )
            )
        ).inRoot(ToastMatcher.withToast()).check(matches(isDisplayed()))
    }

    @Test
    fun holdDataOnViewAfterRotation() {
        val updatedName = "Guo-Xun Liu"
        onView(withId(R.id.ibtnDeveloperEditName)).perform(click())
        onView(withId(R.id.md_input_message)).perform(replaceText(updatedName))
        onView(withText(R.string.mdEditDeveloperButton_text)).perform(click())
        onView(withId(R.id.tvDeveloperName)).check(matches(withText(updatedName)))

        val updatedMotto = "No codes, no life."
        onView(withId(R.id.ibtnDeveloperEditMotto)).perform(click())
        onView(withId(R.id.md_input_message)).perform(replaceText(updatedMotto))
        onView(withText(R.string.mdEditDeveloperButton_text)).perform(click())
        onView(withId(R.id.tvDeveloperMotto)).check(matches(withText(updatedMotto)))

        mainActivityTestResult.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        onView(withId(R.id.tvDeveloperName)).check(matches(withText(updatedName)))
        onView(withId(R.id.tvDeveloperMotto)).check(matches(withText(updatedMotto)))

        mainActivityTestResult.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    @Test
    fun navigateToSettingsFragment() {
        onView(withId(R.id.drawerLayout)).check(matches(isClosed(Gravity.LEFT))).perform(open())
        onView(withId(R.id.settingsFragment)).perform(click())
        onView(withId(R.id.fragmentSettings)).check(matches(isDisplayed()))
    }

    @Test
    fun navigateToSleepNightsFragment() {
        onView(withId(R.id.drawerLayout)).check(matches(isClosed(Gravity.LEFT))).perform(open())
        onView(withId(R.id.sleepNightsFragment)).perform(click())
        onView(withId(R.id.fragmentSleepNights)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        mainActivityTestResult.activity.toast?.cancel()
    }
}
