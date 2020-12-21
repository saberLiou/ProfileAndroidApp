package saberliou.demo.profile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import saberliou.demo.profile.data.source.GithubRepository
import saberliou.demo.profile.data.source.GithubUserDataSource
import saberliou.demo.profile.data.source.IGithubRepository
import saberliou.demo.profile.data.source.local.AppDatabase
import saberliou.demo.profile.data.source.local.GithubUserLocalDataSource
import saberliou.demo.profile.data.source.remote.GithubUserRemoteDataSource
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object GithubRepositoryModule {
    @Qualifier
    annotation class RemoteGithubUserDataSource

    @Qualifier
    annotation class LocalGithubUserDataSource

    @RemoteGithubUserDataSource
    @Singleton
    @Provides
    fun provideGithubUserRemoteDataSource(): GithubUserDataSource {
        return GithubUserRemoteDataSource()
    }

    @LocalGithubUserDataSource
    @Singleton
    @Provides
    fun provideGithubUserLocalDataSource(
        database: AppDatabase,
        ioDispatcher: CoroutineDispatcher
    ): GithubUserDataSource {
        return GithubUserLocalDataSource(database.githubUserDao(), ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideGithubRepository(
        @RemoteGithubUserDataSource remoteDataSource: GithubUserDataSource,
        @LocalGithubUserDataSource localDataSource: GithubUserDataSource,
        ioDispatcher: CoroutineDispatcher
    ): IGithubRepository {
        return GithubRepository(remoteDataSource, localDataSource, ioDispatcher)
    }
}
