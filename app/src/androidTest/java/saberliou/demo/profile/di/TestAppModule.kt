package saberliou.demo.profile.di

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {
    @Provides
    fun provideTargetContext(): Context = InstrumentationRegistry.getInstrumentation().targetContext
}
