package saberliou.demo.profile

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
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
    fun activityLaunched() {
        onView(withId(R.id.ivDeveloperImage)).check(matches(withDrawable(R.drawable.head_photo)))

        // Developer's name
        onView(withId(R.id.tvDeveloperName)).check(matches(withText(R.string.mainActivity_tvDeveloperName_text)))
        onView(withId(R.id.ibtnDeveloperEditName)).check(matches(withDrawable(R.drawable.edit)))

        // Developer's motto
        onView(withId(R.id.tvDeveloperMotto)).check(matches(withText(R.string.mainActivity_tvDeveloperMotto_text)))
        onView(withId(R.id.ibtnDeveloperEditMotto)).check(matches(withDrawable(R.drawable.edit)))
    }

    @Test
    fun useEditDialogsToUpdateDeveloperData() {
        // Developer's name
        val updatedName = "Guo-Xun Liu"
        onView(withId(R.id.ibtnDeveloperEditName)).perform(click())
        onView(withText(R.string.mainActivity_mdEditDeveloperNameTitle_text)).check(
            matches(
                isDisplayed()
            )
        )
        onView(withText(R.string.mainActivity_mdEditDeveloperButton_text)).perform(click())
        onView(withText(R.string.mainActivity_mdEditDeveloperNameTitle_text)).check(
            matches(
                isDisplayed()
            )
        )

        onView(withId(R.id.md_input_message)).perform(typeText(updatedName))
        onView(withText(R.string.mainActivity_mdEditDeveloperButton_text)).perform(click())
        onView(withText(R.string.mainActivity_mdEditDeveloperNameTitle_text)).check(doesNotExist())
        onView(withId(R.id.tvDeveloperName)).check(matches(withText(updatedName)))

        // Developer's motto
        val updatedMotto = "No codes, no life."
        onView(withId(R.id.ibtnDeveloperEditMotto)).perform(click())
        onView(withText(R.string.mainActivity_mdEditDeveloperMottoTitle_text)).check(
            matches(
                isDisplayed()
            )
        )
        onView(withText(R.string.mainActivity_mdEditDeveloperButton_text)).perform(click())
        onView(withText(R.string.mainActivity_mdEditDeveloperMottoTitle_text)).check(
            matches(
                isDisplayed()
            )
        )

        onView(withId(R.id.md_input_message)).perform(typeText(updatedMotto))
        onView(withText(R.string.mainActivity_mdEditDeveloperButton_text)).perform(click())
        onView(withText(R.string.mainActivity_mdEditDeveloperMottoTitle_text)).check(doesNotExist())
        onView(withId(R.id.tvDeveloperMotto)).check(matches(withText(updatedMotto)))
    }
}
