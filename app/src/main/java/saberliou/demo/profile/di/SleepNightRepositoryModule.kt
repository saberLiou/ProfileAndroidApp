package saberliou.demo.profile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import saberliou.demo.profile.data.source.ISleepNightRepository
import saberliou.demo.profile.data.source.SleepNightDataSource
import saberliou.demo.profile.data.source.SleepNightRepository
import saberliou.demo.profile.data.source.local.AppDatabase
import saberliou.demo.profile.data.source.local.SleepNightLocalDataSource
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object SleepNightRepositoryModule {
    @Singleton
    @Provides
    fun provideSleepNightLocalDataSource(
        database: AppDatabase,
        ioDispatcher: CoroutineDispatcher
    ): SleepNightDataSource {
        return SleepNightLocalDataSource(database.sleepNightDao(), ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideSleepNightRepository(
        localDataSource: SleepNightDataSource,
        ioDispatcher: CoroutineDispatcher
    ): ISleepNightRepository {
        return SleepNightRepository(localDataSource, ioDispatcher)
    }
}
