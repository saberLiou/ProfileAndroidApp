package saberliou.demo.profile.util

//import android.content.Context
//import android.net.ConnectivityManager
//import android.net.NetworkCapabilities
//import android.net.NetworkInfo
//import android.os.Build
import android.view.Gravity
import androidx.navigation.fragment.NavHostFragment
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.matcher.ViewMatchers
import saberliou.demo.profile.MainActivity
import saberliou.demo.profile.R

//@Suppress("DEPRECATION")
//fun isNetworkConnected(context: Context): Boolean {
//    val connectivityManager =
//        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        val activeNetworkCapabilities =
//            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//                ?: return false
//        return activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
//                || activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
//    } else {
//        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
//        return activeNetwork?.isConnectedOrConnecting == true
//    }
//}

fun navigateToFragment(activityScenario: ActivityScenario<MainActivity>, navigationId: Int) {
    activityScenario.onActivity { mainActivity ->
        (mainActivity.supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment)
            .navController.navigate(navigationId)
    }
}

fun isNavigationDrawerClosed(isClosed: Boolean = true) {
    Espresso.onView(ViewMatchers.withId(R.id.drawerLayout))
        .check(
            ViewAssertions.matches(
                if (isClosed) DrawerMatchers.isClosed(Gravity.START)
                else DrawerMatchers.isOpen(Gravity.START)
            )
        )
}

fun openNavigationDrawer() {
    Espresso.onView(ViewMatchers.withId(R.id.drawerLayout))
        .perform(DrawerActions.open())
}

fun clickResource(resourceId: Int) {
    Espresso.onView(ViewMatchers.withId(resourceId))
        .perform(ViewActions.click())
}

fun isResourceVisible(resourceId: Int) {
    Espresso.onView(ViewMatchers.withId(resourceId))
        .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
}

fun isResourceDisplaying(resourceId: Int) {
    Espresso.onView(ViewMatchers.withId(resourceId))
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}

fun isResourceWithText(resourceId: Int, text: String) {
    Espresso.onView(ViewMatchers.withId(resourceId))
        .check(ViewAssertions.matches(ViewMatchers.withText(text)))
}

fun isResourceWithHint(resourceId: Int, hint: String) {
    Espresso.onView(ViewMatchers.withId(resourceId))
        .check(ViewAssertions.matches(ViewMatchers.withHint(hint)))
}

fun isResourceClickable(resourceId: Int) {
    Espresso.onView(ViewMatchers.withId(resourceId))
        .check(ViewAssertions.matches(ViewMatchers.isClickable()))
}
