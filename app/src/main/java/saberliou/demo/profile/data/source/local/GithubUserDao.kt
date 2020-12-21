package saberliou.demo.profile.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object for the table of [GithubUserEntity].
 */
@Dao
interface GithubUserDao {
    /**
     * Insert an entity in the database. If the entity already exists, replace it.
     *
     * @param entity the entity to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: GithubUserEntity)

    /**
     * Select the latest entity.
     *
     * @return the latest entity
     */
    @Query("SELECT * FROM ${GithubUserEntity.TABLE_NAME} ORDER BY ${GithubUserEntity.ID} DESC LIMIT 1")
    suspend fun getLatest(): GithubUserEntity?

    /**
     * Observe the latest entity.
     *
     * @return the latest observable entity
     */
    @Query("SELECT * FROM ${GithubUserEntity.TABLE_NAME} ORDER BY ${GithubUserEntity.ID} DESC LIMIT 1")
    fun observeLatest(): LiveData<GithubUserEntity>

    /**
     * Delete all entities.
     */
    @Query("DELETE FROM ${GithubUserEntity.TABLE_NAME}")
    suspend fun deleteAll()
}