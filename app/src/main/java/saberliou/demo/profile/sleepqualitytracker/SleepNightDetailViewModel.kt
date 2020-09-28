package saberliou.demo.profile.sleepqualitytracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class SleepNightDetailViewModel(
    private val nightId: Long = 0L,
    private val sleepNightDao: SleepNightDao
) : ViewModel() {
    val night = liveData {
        emit(sleepNightDao.get(nightId))
    }

    /**
     * Variable that tells the fragment whether it should navigate back to [SleepNightsFragment].
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToSleepNights = MutableLiveData<Boolean?>()

    /**
     * If this is true, immediately navigate back to the [SleepNightsFragment] and call [onSleepNightsNavigationDone].
     */
    val navigateToSleepNights: LiveData<Boolean?>
        get() = _navigateToSleepNights

    /**
     * Call this immediately after navigating back to [SleepNightsFragment]
     */
    fun onSleepNightsNavigationDone() {
        _navigateToSleepNights.value = null
    }

    fun onClose() {
        _navigateToSleepNights.value = true
    }
}
