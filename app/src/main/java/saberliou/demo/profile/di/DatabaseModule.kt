package saberliou.demo.profile.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import saberliou.demo.profile.data.source.local.AppDatabase
import saberliou.demo.profile.data.source.local.GithubUserDao
import saberliou.demo.profile.data.source.local.SleepNightDao
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    private const val DATABASE_NAME = "app-database"

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideGithubUserDao(database: AppDatabase): GithubUserDao {
        return database.githubUserDao()
    }

    @Provides
    fun provideSleepNightDao(database: AppDatabase): SleepNightDao {
        return database.sleepNightDao()
    }
}