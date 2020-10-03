package saberliou.demo.profile

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import saberliou.demo.profile.github.GITHUB_API_URL
import saberliou.demo.profile.github.GithubApiService

private val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())

object RetrofitClient {
    val githubApiService: GithubApiService by lazy {
        retrofitBuilder.baseUrl(GITHUB_API_URL).build().create(GithubApiService::class.java)
    }
}
