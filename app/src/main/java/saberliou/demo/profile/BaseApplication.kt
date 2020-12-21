package saberliou.demo.profile

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication : MultiDexApplication() {
//    val githubRepository: IGithubRepository
//        get() = ServiceLocator.provideGithubRepository(this)

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        // Run long-operation tasks inside a coroutine to avoid delaying app start.
//        CoroutineScope(Dispatchers.Default).launch {
//            if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
//        }
    }
}
