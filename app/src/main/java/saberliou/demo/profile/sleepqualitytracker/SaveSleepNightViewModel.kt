package saberliou.demo.profile.sleepqualitytracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SaveSleepNightViewModel(
    private val nightId: Long = 0L,
    private val sleepNightDao: SleepNightDao
) : ViewModel() {
    /**
     * Variable that tells the fragment whether it should navigate back to [SleepNightsFragment].
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToSleepNights = MutableLiveData<Boolean?>()

    /**
     * If this is true, immediately navigate back to the [SleepNightsFragment] and call [onSleepNightsNavigationDone]
     */
    val navigateToSleepNights: LiveData<Boolean?>
        get() = _navigateToSleepNights

    /**
     * Call this immediately after navigating back to [SleepNightsFragment]
     */
    fun onSleepNightsNavigationDone() {
        _navigateToSleepNights.value = null
    }

    fun onSetQuality(quality: Int) {
        viewModelScope.launch {
            val tonight = sleepNightDao.get(nightId) ?: return@launch
            tonight.quality = quality
            sleepNightDao.update(tonight)

            // Set state to true to navigate back to the SleepNightsFragment.
            _navigateToSleepNights.value = true
        }
    }
}
