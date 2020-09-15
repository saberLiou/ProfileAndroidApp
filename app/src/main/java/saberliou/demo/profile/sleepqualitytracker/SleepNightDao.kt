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

    @Query("SELECT * FROM ${SleepNight.tableName} WHERE ${SleepNight.nightIdName} = :nightId")
    suspend fun get(nightId: Long): SleepNight?

    @Query("SELECT * FROM ${SleepNight.tableName} ORDER BY ${SleepNight.nightIdName} DESC LIMIT 1")
    suspend fun getLatest(): SleepNight?

    @Query("SELECT * FROM ${SleepNight.tableName} ORDER BY ${SleepNight.nightIdName} DESC")
    fun getAll(): LiveData<List<SleepNight>>

    @Update
    suspend fun update(night: SleepNight)

    @Query("DELETE FROM ${SleepNight.tableName}")
    suspend fun deleteAll()
}
