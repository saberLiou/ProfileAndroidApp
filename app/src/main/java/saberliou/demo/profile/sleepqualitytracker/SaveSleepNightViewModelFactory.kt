package saberliou.demo.profile.sleepqualitytracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SaveSleepNightViewModelFactory(
    private val nightId: Long,
    private val sleepNightDao: SleepNightDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SaveSleepNightViewModel::class.java)) return SaveSleepNightViewModel(nightId, sleepNightDao) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}