package saberliou.demo.profile.contactme

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import saberliou.demo.profile.R
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
class ContactMeFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var targetContext: Context

    @Before
    fun setUp() {
        // GIVEN
        hiltRule.inject()

        // WHEN
        launchFragmentInHiltContainer<ContactMeFragment>(targetContext)
    }

    @Test
    fun areComponentsVisibleInFragment() = runBlockingTest {
        // THEN
        isResourceWithText(
            R.id.tvContactMeIntroduction,
            targetContext.resources.getString(R.string.tvContactMeDescription_text)
        )

        isResourceVisible(R.id.tilContactMeTitleLabel)
        isResourceVisible(R.id.tietContactMeTitleText)
        isResourceWithHint(
            R.id.tietContactMeTitleText,
            targetContext.resources.getString(R.string.tilContactMeTitleLabel_hint)
        )

        isResourceVisible(R.id.tilContactMeDescriptionLabel)
        isResourceVisible(R.id.tietContactMeDescriptionText)
        isResourceWithHint(
            R.id.tietContactMeDescriptionText,
            targetContext.resources.getString(R.string.tilContactMeDescriptionLabel_hint)
        )

        isResourceVisible(R.id.btnContactMeSubmit)
        isResourceClickable(R.id.btnContactMeSubmit)
    }
}
