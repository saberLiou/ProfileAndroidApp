package saberliou.demo.profile

import android.os.IBinder
import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

object ToastMatcher {
    fun withToast(): TypeSafeMatcher<Root> =
        object : TypeSafeMatcher<Root>() {
            override fun describeTo(description: Description?) {
                description?.appendText("is toast")
            }

            override fun matchesSafely(item: Root): Boolean {
                val type: Int? = item.windowLayoutParams.get().type
                @Suppress("DEPRECATION")
                if (type == WindowManager.LayoutParams.TYPE_TOAST) {
                    val windowToken: IBinder = item.decorView.windowToken
                    val appToken: IBinder = item.decorView.applicationWindowToken
                    if (windowToken === appToken) {
                        // windowToken == appToken means this window isn't contained by any other windows.
                        // If it was a window for an activity, it would have TYPE_BASE_APPLICATION.
                        return true
                    }
                }
                return false
            }
        }
}
