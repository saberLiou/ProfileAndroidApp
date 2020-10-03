package saberliou.demo.profile.github

import retrofit2.http.GET

const val GITHUB_API_URL = "https://api.github.com/"

interface GithubApiService {
    @GET("/users/saberLiou/repos")
//    fun getRepositories() : Call<List<GithubRepository>>
    suspend fun getRepositories(): List<GithubRepo>
}
