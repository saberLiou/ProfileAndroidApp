package saberliou.demo.profile

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import saberliou.demo.profile.sleepqualitytracker.SleepNight
import saberliou.demo.profile.sleepqualitytracker.SleepNightDao

@Database(
    entities = [SleepNight::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val sleepNightDao: SleepNightDao

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
}
