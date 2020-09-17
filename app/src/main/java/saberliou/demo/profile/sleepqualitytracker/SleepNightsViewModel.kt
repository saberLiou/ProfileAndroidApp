package saberliou.demo.profile.sleepqualitytracker

import android.app.Application
import androidx.lifecycle.*
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

    val isBtnStartTrackingClickable = Transformations.map(tonight) {
        it == null
    }

    val isBtnStopTrackingClickable = Transformations.map(tonight) {
        it != null
    }

    val isBtnClearSleepNightsClickable = Transformations.map(nights) {
        it?.isNotEmpty()
    }

    /**
     * Variable that tells the Fragment to navigate to a specific [SaveSleepNightFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToSaveSleepNight = MutableLiveData<SleepNight>()

    /**
     * If this is non-null, immediately navigate to [SaveSleepNightFragment] and call [onSaveSleepNightNavigationDone]
     */
    val navigateToSaveSleepNight: LiveData<SleepNight>
        get() = _navigateToSaveSleepNight

    /**
     * Call this immediately after navigating to [SaveSleepNightFragment]
     *
     * It will clear the navigation request, so if the user rotates their phone it won't navigate twice.
     */
    fun onSaveSleepNightNavigationDone() {
        _navigateToSaveSleepNight.value = null
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

            // Set state to navigate to the SaveSleepNightFragment.
            _navigateToSaveSleepNight.value = currentNight
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
