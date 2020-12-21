package saberliou.demo.profile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import saberliou.demo.profile.data.source.FakeGithubRepository
import saberliou.demo.profile.data.source.FakeSleepNightRepository
import saberliou.demo.profile.data.source.IGithubRepository
import saberliou.demo.profile.data.source.ISleepNightRepository
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class TestRepositoriesModule {
    @Singleton
    @Binds
    abstract fun bindGithubRepository(repository: FakeGithubRepository): IGithubRepository

    @Singleton
    @Binds
    abstract fun bindSleepNightRepository(repository: FakeSleepNightRepository): ISleepNightRepository
}
