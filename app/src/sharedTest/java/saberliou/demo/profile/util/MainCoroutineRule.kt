package saberliou.demo.profile.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Sets the main coroutines dispatcher to a [TestCoroutineScope] for unit testing. A [TestCoroutineScope] provides control over the execution of coroutines.
 *
 * Declare it as a JUnit Rule:
 *
 * ```
 * @get:Rule
 * var mainCoroutineRule = MainCoroutineRule()
 * ```
 *
 * Use it directly as a [TestCoroutineScope]:
 *
 * ```
 * mainCoroutineRule.pauseDispatcher()
 * ...
 * mainCoroutineRule.resumeDispatcher()
 * ...
 * mainCoroutineRule.runBlockingTest { }
 * ...
 *
 * ```
 */
@ExperimentalCoroutinesApi
class MainCoroutineRule(private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
    TestWatcher(),
    TestCoroutineScope by TestCoroutineScope(dispatcher) {

    /**
     * Invoked when a test is about to start in @Before.
     */
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    /**
     * Invoked when a test method finishes (whether passing or failing) in @After.
     */
    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}
