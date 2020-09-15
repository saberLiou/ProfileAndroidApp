package saberliou.demo.profile.sleepqualitytracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SleepNightsViewModelFactory(
    private val application: Application,
    private val sleepNightDao: SleepNightDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepNightsViewModel::class.java)) return SleepNightsViewModel(application, sleepNightDao) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
