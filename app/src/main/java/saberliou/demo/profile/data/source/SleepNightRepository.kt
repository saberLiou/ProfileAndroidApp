package saberliou.demo.profile.data.source

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import saberliou.demo.profile.SleepNight
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.util.wrapEspressoIdlingResource

interface ISleepNightRepository {
    suspend fun createSleepNight(night: SleepNight)
    suspend fun getSleepNight(id: Long): Result<SleepNight>
    suspend fun getLatestSleepNight(): Result<SleepNight>
    fun observeSleepNights(): LiveData<Result<List<SleepNight>>>
    suspend fun refreshSleepNights()
    suspend fun updateSleepNight(night: SleepNight)
    suspend fun deleteSleepNights()
}

class SleepNightRepository(
    private val localDataSource: SleepNightDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ISleepNightRepository {
    override suspend fun createSleepNight(night: SleepNight) {
        wrapEspressoIdlingResource {
            localDataSource.createSleepNight(night)
        }
    }

    override suspend fun getSleepNight(id: Long): Result<SleepNight> {
        wrapEspressoIdlingResource {
            return localDataSource.getSleepNight(id)
        }
    }

    override suspend fun getLatestSleepNight(): Result<SleepNight> {
        wrapEspressoIdlingResource {
            return localDataSource.getLatestSleepNight()
        }
    }

    override fun observeSleepNights(): LiveData<Result<List<SleepNight>>> {
        wrapEspressoIdlingResource {
            return localDataSource.observeSleepNights()
        }
    }

    override suspend fun refreshSleepNights() {
        TODO("Not yet implemented")
    }

    override suspend fun updateSleepNight(night: SleepNight) {
        wrapEspressoIdlingResource {
            localDataSource.updateSleepNight(night)
        }
    }

    override suspend fun deleteSleepNights() {
        wrapEspressoIdlingResource {
            localDataSource.deleteSleepNights()
        }
    }
}