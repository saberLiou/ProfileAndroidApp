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

        @Volatile
        private var instance: AppDatabase? = null

        // synchronized(lock) will consume resources, so
        fun getInstance(context: Context): AppDatabase = instance ?: synchronized(this) {
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
