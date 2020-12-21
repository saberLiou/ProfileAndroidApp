package saberliou.demo.profile.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import saberliou.demo.profile.SleepNight
import saberliou.demo.profile.SleepNight.Companion.toDatabaseEntity
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.data.Result.Error
import saberliou.demo.profile.data.Result.Success
import saberliou.demo.profile.data.source.SleepNightDataSource
import saberliou.demo.profile.data.source.SleepNightDataSource.Companion.SLEEP_NIGHT_NOT_FOUND
import saberliou.demo.profile.data.source.local.SleepNightEntity.Companion.toDomainModel
import saberliou.demo.profile.data.source.local.SleepNightEntity.Companion.toDomainModels

class SleepNightLocalDataSource(
    private val sleepNightDao: SleepNightDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SleepNightDataSource {
    override suspend fun createSleepNight(night: SleepNight) {
        sleepNightDao.insert(night.toDatabaseEntity())
    }

    override suspend fun getSleepNight(id: Long): Result<SleepNight> = withContext(ioDispatcher) {
        return@withContext try {
            val nightEntity = sleepNightDao.get(id)
            if (nightEntity != null) {
                Success(nightEntity.toDomainModel())
            } else {
                throw Exception(SLEEP_NIGHT_NOT_FOUND)
            }
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getLatestSleepNight(): Result<SleepNight> = withContext(ioDispatcher) {
        return@withContext try {
            val nightEntity = sleepNightDao.getLatest()
            if (nightEntity != null) {
                Success(nightEntity.toDomainModel())
            } else {
                throw Exception(SLEEP_NIGHT_NOT_FOUND)
            }
        } catch (e: Exception) {
            Error(e)
        }
    }

    override fun observeSleepNights(): LiveData<Result<List<SleepNight>>> {
        return sleepNightDao.observeAll().map {
            Success(it.toDomainModels())
        }
    }

    override suspend fun updateSleepNight(night: SleepNight) {
        sleepNightDao.update(night.toDatabaseEntity())
    }

    override suspend fun deleteSleepNights() {
        sleepNightDao.deleteAll()
    }
}