package saberliou.demo.profile.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import saberliou.demo.profile.SleepNight
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.data.Result.Companion.isSuccess
import saberliou.demo.profile.data.Result.Error
import saberliou.demo.profile.data.Result.Success
import saberliou.demo.profile.data.source.SleepNightDataSource.Companion.SLEEP_NIGHT_NOT_FOUND
import javax.inject.Inject

class FakeSleepNightRepository @Inject constructor() : ISleepNightRepository {
    private var nights: Result<ArrayList<SleepNight>> = Success(ArrayList())
    private val observableNights = MutableLiveData<Result<List<SleepNight>>>()

    override suspend fun createSleepNight(night: SleepNight) {
        if (nights.isSuccess()) {
            (nights as Success).data.add(night)
            refreshSleepNights()
        }
    }

    override suspend fun getSleepNight(id: Long): Result<SleepNight> {
        if (nights.isSuccess()) {
            (nights as Success).data.firstOrNull { it.id == id }?.let {
                return Success(it)
            }
        }
        return Error(Exception(SLEEP_NIGHT_NOT_FOUND))
    }

    override suspend fun getLatestSleepNight(): Result<SleepNight> {
        if (nights.isSuccess()) {
            (nights as Success).data.maxByOrNull { it.id }?.let {
                return Success(it)
            }
        }
        return Error(Exception(SLEEP_NIGHT_NOT_FOUND))
    }

    override fun observeSleepNights(): LiveData<Result<List<SleepNight>>> {
        return observableNights
    }

    override suspend fun refreshSleepNights() {
        observableNights.value = nights
    }

    override suspend fun updateSleepNight(night: SleepNight) {
        if (nights.isSuccess()) {
            val nightsList = (nights as Success).data
            nightsList.indexOfFirst { it.id == night.id }.let {
                if (it > 0) nightsList[it] = night
            }
            refreshSleepNights()
        }
    }

    override suspend fun deleteSleepNights() {
        if (nights.isSuccess()) {
            (nights as Success).data.clear()
            refreshSleepNights()
        }
    }
}
