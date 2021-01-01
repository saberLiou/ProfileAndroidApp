package saberliou.demo.profile.data.source.remote

import retrofit2.http.GET

object GithubApiUrls {
    const val BASE_URL = "https://api.github.com/"

    const val GET_USER = "/users/saberLiou"
    const val GET_REPOSITORIES = "/users/saberLiou/repos"
}

interface GithubApiService {
    @GET(GithubApiUrls.GET_USER)
    suspend fun getUser(): RemoteGithubUser

    @GET(GithubApiUrls.GET_REPOSITORIES)
    suspend fun getRepositories(): List<RemoteGithubRepo>
}
