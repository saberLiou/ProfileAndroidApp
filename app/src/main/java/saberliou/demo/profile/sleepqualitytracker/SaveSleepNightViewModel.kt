package saberliou.demo.profile.sleepqualitytracker

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import saberliou.demo.profile.data.Result.Companion.isSuccess
import saberliou.demo.profile.data.Result.Success
import saberliou.demo.profile.data.source.ISleepNightRepository
import saberliou.demo.profile.util.Event

class SaveSleepNightViewModel @ViewModelInject constructor(
    private val sleepNightRepository: ISleepNightRepository
) : ViewModel() {
    /**
     * Variable that tells the fragment when should it navigate back to [SleepNightsFragment].
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToSleepNights = MutableLiveData<Event<Unit>>()
    val navigateToSleepNights: LiveData<Event<Unit>>
        get() = _navigateToSleepNights

    private var id: Long = 0L

    fun initialize(id: Long) {
        this.id = id
    }

    fun onQualitySelected(quality: Int) {
        viewModelScope.launch {
            val tonight = sleepNightRepository.getSleepNight(id)
            if (!tonight.isSuccess()) return@launch
            sleepNightRepository.updateSleepNight((tonight as Success).data.apply {
                this.quality = quality
            })

            // Set state to navigate back to the SleepNightsFragment.
            _navigateToSleepNights.value = Event(Unit)
        }
    }
}
