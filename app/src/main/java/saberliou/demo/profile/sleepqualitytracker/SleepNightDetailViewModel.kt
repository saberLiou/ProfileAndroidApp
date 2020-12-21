package saberliou.demo.profile.sleepqualitytracker

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import saberliou.demo.profile.data.Result.Success
import saberliou.demo.profile.data.source.ISleepNightRepository
import saberliou.demo.profile.util.Event

class SleepNightDetailViewModel @ViewModelInject constructor(
    private val sleepNightRepository: ISleepNightRepository
) : ViewModel() {
    /**
     * Variable that tells the fragment when should it navigate back to [SleepNightsFragment].
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToSleepNights = MutableLiveData<Event<Unit>>()
    val navigateToSleepNights: LiveData<Event<Unit>>
        get() = _navigateToSleepNights

    private val _id = MutableLiveData<Long>()
    val night = _id.switchMap { id ->
        liveData { emit(sleepNightRepository.getSleepNight(id)) }.map { nightResult ->
            if (nightResult is Success) {
                nightResult.data
            } else {
                null
            }
        }
    }

    fun initialize(id: Long) {
        _id.value = id
    }

    fun onClose() {
        _navigateToSleepNights.value = Event(Unit)
    }
}
