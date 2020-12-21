package saberliou.demo.profile.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        SleepNightEntity::class,
        GithubUserEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    /*
    companion object {
        private const val DATABASE_NAME = "app-database"

        // The value of a volatile variable will never be cached, and all writes and reads will be done to and from the main memory.
        // This helps make sure the value of `instance` is always up-to-date and the same to all execution threads.
        @Volatile
        private var instance: AppDatabase? = null

        // The lock of synchronized block will consume some resources, so use "instance ?:" in front of it to directly access the instance if it has been already created and existed.
        fun getInstance(context: Context): AppDatabase = instance ?: synchronized(this) {
            // The second "instance ?:" here is to check again if there’s any existing instance returned from other threads after currently locking.
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
                .also { instance = it }
        }
    }

    abstract val githubUserDao: GithubUserDao
    abstract val sleepNightDao: SleepNightDao
    */

    abstract fun githubUserDao(): GithubUserDao
    abstract fun sleepNightDao(): SleepNightDao
}
