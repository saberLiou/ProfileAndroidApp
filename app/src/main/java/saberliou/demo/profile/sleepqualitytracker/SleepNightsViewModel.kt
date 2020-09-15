package saberliou.demo.profile.sleepqualitytracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SleepNightsViewModel(
    application: Application,
    private val sleepNightDao: SleepNightDao,
) : AndroidViewModel(application) {
    private var tonight = MutableLiveData<SleepNight?>()
    private val nights = sleepNightDao.getAll()
    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }

    init {
        viewModelScope.launch {
            setTonight()
        }
    }

    fun onStartTracking() {
        viewModelScope.launch {
            sleepNightDao.insert(SleepNight())
            setTonight()
        }
    }

    fun onStopTracking() {
        viewModelScope.launch {
            val currentNight = tonight.value ?: return@launch
            currentNight.endTime = System.currentTimeMillis()
            sleepNightDao.update(currentNight)
        }
    }

    fun onClearSleepNights() {
        viewModelScope.launch {
            sleepNightDao.deleteAll()
            tonight.value = null
        }
    }

    private suspend fun setTonight() {
        tonight.value = sleepNightDao.getLatest()
        if (tonight.value?.endTime != tonight.value?.startTime) tonight.value = null
    }
}
