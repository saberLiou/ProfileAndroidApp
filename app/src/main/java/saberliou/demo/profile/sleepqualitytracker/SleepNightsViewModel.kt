package saberliou.demo.profile.sleepqualitytracker

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import saberliou.demo.profile.SleepNight
import saberliou.demo.profile.data.Result.Success
import saberliou.demo.profile.data.source.ISleepNightRepository
import saberliou.demo.profile.util.Event

class SleepNightsViewModel @ViewModelInject constructor(
    private val sleepNightRepository: ISleepNightRepository
) : ViewModel() {
    private var tonight = MutableLiveData<SleepNight?>()
    val nights = sleepNightRepository.observeSleepNights().switchMap { nightsResult ->
        MutableLiveData<List<SleepNight>>().apply {
            value = if (nightsResult is Success) nightsResult.data else emptyList()
        }
    }

    val isBtnStartTrackingClickable = tonight.map {
        it == null
    }

    val isBtnStopTrackingClickable = tonight.map {
        it != null
    }

    val isBtnClearSleepNightsClickable = nights.map {
        it.isNotEmpty()
    }

    /**
     * Variable that tells the Fragment when it should navigate to a specific [SaveSleepNightFragment].
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToSaveSleepNight = MutableLiveData<Event<Long>>()
    val navigateToSaveSleepNightEntity: LiveData<Event<Long>>
        get() = _navigateToSaveSleepNight

    /**
     * Variable that tells the Fragment when it should navigate to a specific [SleepNightDetailFragment].
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToSleepNightDetail = MutableLiveData<Event<Long>>()
    val navigateToSleepNightEntityDetail: LiveData<Event<Long>>
        get() = _navigateToSleepNightDetail

    fun onSleepNightClicked(id: Long) {
        _navigateToSleepNightDetail.value = Event(id)
    }

    /**
     * Variable that tells the Fragment when it should show a SnackBar.
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private var _showSnackbar = MutableLiveData<Event<Unit>>()
    val showSnackbar: LiveData<Event<Unit>>
        get() = _showSnackbar

    init {
        viewModelScope.launch {
            setTonight()
        }
    }

    fun onStartTracking() {
        viewModelScope.launch {
            sleepNightRepository.createSleepNight(SleepNight())
            setTonight()
        }
    }

    fun onStopTracking() {
        viewModelScope.launch {
            val currentNight = tonight.value ?: return@launch
            currentNight.endTime = System.currentTimeMillis()
            sleepNightRepository.updateSleepNight(currentNight)

            // Set state to navigate to the SaveSleepNightFragment.
            _navigateToSaveSleepNight.value = Event(currentNight.id)
        }
    }

    fun onClearSleepNights() {
        viewModelScope.launch {
            sleepNightRepository.deleteSleepNights()
            tonight.value = null

            // Set state to show a Snackbar.
            _showSnackbar.value = Event(Unit)
        }
    }

    private suspend fun setTonight() {
        val latestNightResult = sleepNightRepository.getLatestSleepNight()
        tonight.value = if (latestNightResult is Success) latestNightResult.data else null
        if (tonight.value?.endTime != tonight.value?.startTime) tonight.value = null
    }
}
