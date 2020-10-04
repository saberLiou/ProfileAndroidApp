package saberliou.demo.profile

import androidx.multidex.MultiDexApplication
import timber.log.Timber

class BaseApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}
