package saberliou.demo.profile.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SleepNightDao {
    /**
     * Insert an entity in the database.
     *
     * @param entity the entity to be inserted
     */
    @Insert
    suspend fun insert(entity: SleepNightEntity)

    /**
     * Select an entity by id.
     *
     * @param id the entity id
     * @return the entity with id
     */
    @Query("SELECT * FROM ${SleepNightEntity.TABLE_NAME} WHERE ${SleepNightEntity.ID} = :id")
    suspend fun get(id: Long): SleepNightEntity?

    /**
     * Select the latest entity.
     *
     * @return the latest entity
     */
    @Query("SELECT * FROM ${SleepNightEntity.TABLE_NAME} ORDER BY ${SleepNightEntity.ID} DESC LIMIT 1")
    suspend fun getLatest(): SleepNightEntity?

    /**
     * Observe all entities.
     *
     * @return all observable entities
     */
    @Query("SELECT * FROM ${SleepNightEntity.TABLE_NAME} ORDER BY ${SleepNightEntity.ID} DESC")
    fun observeAllDescending(): LiveData<List<SleepNightEntity>>

    /**
     * Insert the entity in the database.
     *
     * @param entity the entity to be updated
     */
    @Update
    suspend fun update(entity: SleepNightEntity)

    /**
     * Delete all entities.
     */
    @Query("DELETE FROM ${SleepNightEntity.TABLE_NAME}")
    suspend fun deleteAll()
}
