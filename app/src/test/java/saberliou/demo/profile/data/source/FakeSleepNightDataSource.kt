package saberliou.demo.profile.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import saberliou.demo.profile.SleepNight
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.data.source.SleepNightDataSource.Companion.SLEEP_NIGHT_NOT_FOUND

class FakeSleepNightDataSource : SleepNightDataSource {
    private val nights: ArrayList<SleepNight> = arrayListOf()
    private val observableNights = MutableLiveData<Result<List<SleepNight>>>()

    override suspend fun createSleepNight(night: SleepNight) {
        nights.add(night)
        refreshSleepNights()
    }

    override suspend fun getSleepNight(id: Long): Result<SleepNight> {
        nights.firstOrNull { it.id == id }?.let { return Result.Success(it) }
        return Result.Error(Exception(SLEEP_NIGHT_NOT_FOUND))
    }

    override suspend fun getLatestSleepNight(): Result<SleepNight> {
        nights.maxByOrNull { it.id }?.let { return Result.Success(it) }
        return Result.Error(Exception(SLEEP_NIGHT_NOT_FOUND))
    }

    override fun observeSleepNights(): LiveData<Result<List<SleepNight>>> {
        return observableNights
    }

    override suspend fun refreshSleepNights() {
        observableNights.value = Result.Success(nights)
    }

    override suspend fun updateSleepNight(night: SleepNight) {
        nights.indexOfFirst { it.id == night.id }.let {
            if (it > -1) nights[it] = night
        }
        refreshSleepNights()
    }

    override suspend fun deleteSleepNights() {
        nights.clear()
        refreshSleepNights()
    }
}
