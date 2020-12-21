package saberliou.demo.profile.util

import androidx.fragment.app.FragmentActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class DataBindingIdlingResourceRule : TestWatcher() {
    // An Idling Resource that waits for Data Binding to have no pending bindings
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    /**
     * Invoked when a test is about to start in @Before.
     */
    override fun starting(description: Description?) {
        super.starting(description)
        // Idling resources tell Espresso that the app is idle or busy. This is needed when operations
        // are not scheduled in the main Looper (for example when executed on a different thread).
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    /**
     * Invoked when a test method finishes (whether passing or failing) in @After.
     */
    override fun finished(description: Description?) {
        super.finished(description)
        // Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    fun monitorActivity(activityScenario: ActivityScenario<out FragmentActivity>) {
        dataBindingIdlingResource.monitorActivity(activityScenario)
    }
}
