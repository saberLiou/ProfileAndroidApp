package saberliou.demo.profile.sleepqualitytracker

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SleepNightDao {
    @Insert
    suspend fun insert(night: SleepNight)

    @Query("SELECT * FROM ${SleepNight.TABLE_NAME} WHERE ${SleepNight.NIGHT_ID} = :nightId")
    suspend fun get(nightId: Long): SleepNight?

    @Query("SELECT * FROM ${SleepNight.TABLE_NAME} ORDER BY ${SleepNight.NIGHT_ID} DESC LIMIT 1")
    suspend fun getLatest(): SleepNight?

    @Query("SELECT * FROM ${SleepNight.TABLE_NAME} ORDER BY ${SleepNight.NIGHT_ID} DESC")
    fun getAll(): LiveData<List<SleepNight>>

    @Update
    suspend fun update(night: SleepNight)

    @Query("DELETE FROM ${SleepNight.TABLE_NAME}")
    suspend fun deleteAll()
}
