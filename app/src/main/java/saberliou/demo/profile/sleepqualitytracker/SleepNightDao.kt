package saberliou.demo.profile.sleepqualitytracker

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SleepNightDao {
    @Insert
    fun insert(night: SleepNight)

    @Query("SELECT * FROM ${SleepNight.tableName} WHERE ${SleepNight.nightIdName} = :nightId")
    fun getNight(nightId: Long): SleepNight?

    @Query("SELECT * FROM ${SleepNight.tableName} ORDER BY ${SleepNight.nightIdName} DESC LIMIT 1")
    fun getTonight(): SleepNight?

    @Query("SELECT * FROM ${SleepNight.tableName} ORDER BY ${SleepNight.nightIdName} DESC")
    fun getAllNights(): LiveData<List<SleepNight>>

    @Update
    fun update(night: SleepNight)

    @Query("DELETE FROM ${SleepNight.tableName}")
    fun deleteAllNights()
}
