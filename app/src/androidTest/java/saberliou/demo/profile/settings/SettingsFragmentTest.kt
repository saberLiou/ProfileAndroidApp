package saberliou.demo.profile.settings

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import saberliou.demo.profile.R
import saberliou.demo.profile.di.GithubRepositoryModule
import saberliou.demo.profile.di.SleepNightRepositoryModule
import saberliou.demo.profile.util.isResourceWithText
import saberliou.demo.profile.util.launchFragmentInHiltContainer
import javax.inject.Inject

@LargeTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(
    GithubRepositoryModule::class,
    SleepNightRepositoryModule::class
)
class SettingsFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var targetContext: Context

    @Before
    fun setUp() {
        // GIVEN
        hiltRule.inject()

        // WHEN
        launchFragmentInHiltContainer<SettingsFragment>(targetContext)
    }

    @Test
    fun areComponentsVisibleInFragment() = runBlockingTest {
        // THEN
        isResourceWithText(
            R.id.tvUnderConstructionDescription,
            targetContext.resources.getString(R.string.tvUnderConstructionDescription_text)
        )
    }
}
