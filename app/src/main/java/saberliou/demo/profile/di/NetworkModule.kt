package saberliou.demo.profile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import saberliou.demo.profile.data.source.remote.GithubApiService
import saberliou.demo.profile.data.source.remote.GithubApiUrls
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder().addConverterFactory(
        GsonConverterFactory.create()
    )

    @Singleton
    @Provides
    fun provideGithubApiService(retrofitBuilder: Retrofit.Builder): GithubApiService =
        retrofitBuilder.baseUrl(GithubApiUrls.BASE_URL).build().create(GithubApiService::class.java)
}
