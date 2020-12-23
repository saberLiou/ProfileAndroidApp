package saberliou.demo.profile.data.source

import androidx.lifecycle.LiveData
import saberliou.demo.profile.SleepNight
import saberliou.demo.profile.data.Result

interface SleepNightDataSource {
    companion object {
        const val SLEEP_NIGHT_NOT_FOUND = "SleepNight not found!"
    }

    suspend fun createSleepNight(night: SleepNight)
    suspend fun getSleepNight(id: Long): Result<SleepNight>
    suspend fun getLatestSleepNight(): Result<SleepNight>
    fun observeSleepNights(): LiveData<Result<List<SleepNight>>>
    suspend fun refreshSleepNights()
    suspend fun updateSleepNight(night: SleepNight)
    suspend fun deleteSleepNights()
}