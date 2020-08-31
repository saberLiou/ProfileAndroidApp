package saberliou.demo.profile

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import saberliou.demo.profile.ImageViewDrawableMatcher.withDrawable
import saberliou.demo.profile.MainActivity.Companion.UpdateTypes.MOTTO
import saberliou.demo.profile.MainActivity.Companion.UpdateTypes.NAME
import saberliou.demo.profile.MainActivity.Companion.makeToastString
import saberliou.demo.profile.ToastMatcher.withToast

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var mainActivityTestResult = ActivityTestRule(MainActivity::class.java)

    @Test
    fun activityLaunched() {
        val developer = Developer()
        onView(withId(R.id.ivDeveloperImage)).check(matches(withDrawable(R.drawable.head_photo)))

        // Developer's name
        onView(withId(R.id.tvDeveloperName)).check(matches(withText(developer.name)))
        onView(withId(R.id.ibtnDeveloperEditName)).check(matches(withDrawable(R.drawable.edit)))

        // Developer's motto
        onView(withId(R.id.tvDeveloperMotto)).check(matches(withText(developer.motto)))
        onView(withId(R.id.ibtnDeveloperEditMotto)).check(matches(withDrawable(R.drawable.edit)))
    }

    @Test
    fun updateDeveloperName() {
        val updatedName = "Guo-Xun Liu"
        onView(withId(R.id.ibtnDeveloperEditName)).perform(click())
        onView(withText(R.string.mainActivity_mdEditDeveloperNameTitle_text)).check(
            matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.md_input_message)).perform(closeSoftKeyboard())  // To prevent zh soft keyboard influence the positive button of Material Dialogs.
        onView(withText(R.string.mainActivity_mdEditDeveloperButton_text)).perform(click())
        onView(withText(R.string.mainActivity_mdEditDeveloperNameTitle_text)).check(
            matches(
                isDisplayed()
            )
        )

        onView(withId(R.id.md_input_message)).perform(click(), replaceText(updatedName))
        onView(withText(R.string.mainActivity_mdEditDeveloperButton_text)).perform(click())
        onView(withText(R.string.mainActivity_mdEditDeveloperNameTitle_text)).check(doesNotExist())
        onView(withId(R.id.tvDeveloperName)).check(matches(withText(updatedName)))

        onView(withText(makeToastString(NAME.getType(), updatedName))).inRoot(withToast())
            .check(matches(isDisplayed()))
    }

    @Test
    fun updateDeveloperMotto() {
        val updatedMotto = "No codes, no life."
        onView(withId(R.id.ibtnDeveloperEditMotto)).perform(click())
        onView(withText(R.string.mainActivity_mdEditDeveloperMottoTitle_text)).check(
            matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.md_input_message)).perform(closeSoftKeyboard())  // To prevent zh soft keyboard influence the positive button of Material Dialogs.
        onView(withText(R.string.mainActivity_mdEditDeveloperButton_text)).perform(click())
        onView(withText(R.string.mainActivity_mdEditDeveloperMottoTitle_text)).check(
            matches(
                isDisplayed()
            )
        )

        onView(withId(R.id.md_input_message)).perform(replaceText(updatedMotto))
        onView(withText(R.string.mainActivity_mdEditDeveloperButton_text)).perform(click())
        onView(withText(R.string.mainActivity_mdEditDeveloperMottoTitle_text)).check(doesNotExist())
        onView(withId(R.id.tvDeveloperMotto)).check(matches(withText(updatedMotto)))

        onView(withText(makeToastString(MOTTO.getType(), updatedMotto))).inRoot(withToast())
            .check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        mainActivityTestResult.activity.toast?.cancel()
    }
}
