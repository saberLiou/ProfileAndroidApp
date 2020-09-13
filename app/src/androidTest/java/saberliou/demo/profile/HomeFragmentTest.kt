package saberliou.demo.profile

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
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
        Espresso.onView(ViewMatchers.withId(R.id.ivDeveloperImage))
            .check(ViewAssertions.matches(ImageViewDrawableMatcher.withDrawable(R.drawable.head_photo)))

        // Developer's name
        Espresso.onView(ViewMatchers.withId(R.id.tvDeveloperName))
            .check(ViewAssertions.matches(ViewMatchers.withText(developer.name)))
        Espresso.onView(ViewMatchers.withId(R.id.ibtnDeveloperEditName))
            .check(ViewAssertions.matches(ImageViewDrawableMatcher.withDrawable(R.drawable.edit)))

        // Developer's motto
        Espresso.onView(ViewMatchers.withId(R.id.tvDeveloperMotto))
            .check(ViewAssertions.matches(ViewMatchers.withText(developer.motto)))
        Espresso.onView(ViewMatchers.withId(R.id.ibtnDeveloperEditMotto))
            .check(ViewAssertions.matches(ImageViewDrawableMatcher.withDrawable(R.drawable.edit)))
    }

    @Test
    fun updateDeveloperName() {
        val updatedName = "Guo-Xun Liu"
        Espresso.onView(ViewMatchers.withId(R.id.ibtnDeveloperEditName))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(R.string.homeFragment_mdEditDeveloperNameTitle_text)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.md_input_message))
            .perform(ViewActions.closeSoftKeyboard())  // To prevent zh soft keyboard influence the positive button of Material Dialogs.
        Espresso.onView(ViewMatchers.withText(R.string.homeFragment_mdEditDeveloperButton_text))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(R.string.homeFragment_mdEditDeveloperNameTitle_text)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.md_input_message))
            .perform(ViewActions.click(), ViewActions.replaceText(updatedName))
        Espresso.onView(ViewMatchers.withText(R.string.homeFragment_mdEditDeveloperButton_text))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(R.string.homeFragment_mdEditDeveloperNameTitle_text))
            .check(ViewAssertions.doesNotExist())
        Espresso.onView(ViewMatchers.withId(R.id.tvDeveloperName))
            .check(ViewAssertions.matches(ViewMatchers.withText(updatedName)))

        Espresso.onView(
            ViewMatchers.withText(
                HomeFragment.makeToastString(
                    HomeFragment.Companion.UpdateTypes.NAME.getType(),
                    updatedName
                )
            )
        ).inRoot(ToastMatcher.withToast())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun updateDeveloperMotto() {
        val updatedMotto = "No codes, no life."
        Espresso.onView(ViewMatchers.withId(R.id.ibtnDeveloperEditMotto))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(R.string.homeFragment_mdEditDeveloperMottoTitle_text))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
        )
        Espresso.onView(ViewMatchers.withId(R.id.md_input_message))
            .perform(ViewActions.closeSoftKeyboard())  // To prevent zh soft keyboard influence the positive button of Material Dialogs.
        Espresso.onView(ViewMatchers.withText(R.string.homeFragment_mdEditDeveloperButton_text))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(R.string.homeFragment_mdEditDeveloperMottoTitle_text))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
        )

        Espresso.onView(ViewMatchers.withId(R.id.md_input_message))
            .perform(ViewActions.replaceText(updatedMotto))
        Espresso.onView(ViewMatchers.withText(R.string.homeFragment_mdEditDeveloperButton_text))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(R.string.homeFragment_mdEditDeveloperMottoTitle_text))
            .check(ViewAssertions.doesNotExist())
        Espresso.onView(ViewMatchers.withId(R.id.tvDeveloperMotto))
            .check(ViewAssertions.matches(ViewMatchers.withText(updatedMotto)))

        Espresso.onView(
            ViewMatchers.withText(
                HomeFragment.makeToastString(
                    HomeFragment.Companion.UpdateTypes.MOTTO.getType(),
                    updatedMotto
                )
            )
        ).inRoot(ToastMatcher.withToast())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun holdDataOnViewAfterRotation() {
        val updatedName = "Guo-Xun Liu"
        Espresso.onView(ViewMatchers.withId(R.id.ibtnDeveloperEditName))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.md_input_message))
            .perform(ViewActions.replaceText(updatedName))
        Espresso.onView(ViewMatchers.withText(R.string.homeFragment_mdEditDeveloperButton_text))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.tvDeveloperName))
            .check(ViewAssertions.matches(ViewMatchers.withText(updatedName)))

        val updatedMotto = "No codes, no life."
        Espresso.onView(ViewMatchers.withId(R.id.ibtnDeveloperEditMotto))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.md_input_message))
            .perform(ViewActions.replaceText(updatedMotto))
        Espresso.onView(ViewMatchers.withText(R.string.homeFragment_mdEditDeveloperButton_text))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.tvDeveloperMotto))
            .check(ViewAssertions.matches(ViewMatchers.withText(updatedMotto)))

        mainActivityTestResult.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        Espresso.onView(ViewMatchers.withId(R.id.tvDeveloperName))
            .check(ViewAssertions.matches(ViewMatchers.withText(updatedName)))
        Espresso.onView(ViewMatchers.withId(R.id.tvDeveloperMotto))
            .check(ViewAssertions.matches(ViewMatchers.withText(updatedMotto)))

        mainActivityTestResult.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    @After
    fun tearDown() {
        mainActivityTestResult.activity.toast?.cancel()
    }
}