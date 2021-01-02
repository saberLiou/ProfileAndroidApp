package saberliou.demo.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

//    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
//    var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        navController =
            (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment).navController
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.settingsFragment,
            R.id.githubRepositoriesFragment,
            R.id.todoNotesFragment,
            R.id.sleepNightsFragment
        ).setOpenableLayout(findViewById(R.id.drawerLayout)).build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.navView).setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    fun showToast(message: String) {
//        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)?.also { it.show() }
//    }
}
