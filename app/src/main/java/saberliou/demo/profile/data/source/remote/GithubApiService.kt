package saberliou.demo.profile.data.source.remote

import retrofit2.http.GET

//const val GITHUB_API_URL = "https://api.github.com/"

interface GithubApiService {
    @GET("/users/saberLiou")
    suspend fun getUser(): RemoteGithubUser

    @GET("/users/saberLiou/repos")
    suspend fun getRepositories(): List<RemoteGithubRepo>
}
