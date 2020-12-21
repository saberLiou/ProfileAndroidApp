package saberliou.demo.profile.data.source.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())

object RetrofitClient {
    val githubApiService: GithubApiService by lazy {
        retrofitBuilder.baseUrl(GITHUB_API_URL).build().create(GithubApiService::class.java)
    }
}
