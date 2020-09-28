package saberliou.demo.profile.sleepqualitytracker

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class SleepNightsViewModel(
    application: Application,
    private val sleepNightDao: SleepNightDao,
) : AndroidViewModel(application) {
    private var tonight = MutableLiveData<SleepNight?>()
    val nights = sleepNightDao.getAll()
//    val nightsString = Transformations.map(nights) { nights ->
//        formatNights(nights, application.resources)
//    }

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

    /**
     * Variable that tells the Fragment to navigate to a specific [SleepNightDetailFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToSleepNightDetail = MutableLiveData<SleepNight>()

    /**
     * If this is non-null, immediately navigate to [SleepNightDetailFragment] and call [onSleepNightDetailNavigationDone]
     */
    val navigateToSleepNightDetail: LiveData<SleepNight>
        get() = _navigateToSleepNightDetail

    /**
     * Call this immediately after navigating to [SleepNightDetailFragment]
     *
     * It will clear the navigation request, so if the user rotates their phone it won't navigate twice.
     */
    fun onSleepNightDetailNavigationDone() {
        _navigateToSleepNightDetail.value = null
    }

    fun onSleepNightClicked(night: SleepNight) {
        _navigateToSleepNightDetail.value = night
    }

    /**
     * Variable that tells the Fragment to show a SnackBar.
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private var _showSnackbar = MutableLiveData<Boolean>()

    /**
     * If this is true, immediately show a Snackbar and call `doneShowingSnackbar()`.
     */
    val showSnackbar: LiveData<Boolean>
        get() = _showSnackbar

    /**
     * Call this immediately after showing a Snackbar.
     *
     * It will clear the showing request, so if the user rotates their phone it won't show a duplicate Snackbar.
     */
    fun onSnackbarShown() {
        _showSnackbar.value = false
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

            // Set state to true to show a Snackbar.
            _showSnackbar.value = true
        }
    }

    private suspend fun setTonight() {
        tonight.value = sleepNightDao.getLatest()
        if (tonight.value?.endTime != tonight.value?.startTime) tonight.value = null
    }
}
