package saberliou.demo.profile.data.source

import androidx.lifecycle.LiveData
import saberliou.demo.profile.GithubUser
import saberliou.demo.profile.data.Result

interface GithubUserDataSource {
    companion object {
        const val GITHUB_USER_NOT_FOUND = "GithubUser not found!"
    }

    suspend fun setGithubUser(githubUser: GithubUser)
    suspend fun getGithubUser(): Result<GithubUser>
    suspend fun refreshGithubUser()
    fun observeGithubUser(): LiveData<Result<GithubUser>>
    suspend fun clearGithubUser()
}
