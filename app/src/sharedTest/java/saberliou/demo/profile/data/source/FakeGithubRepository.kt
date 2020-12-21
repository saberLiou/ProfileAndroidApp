package saberliou.demo.profile.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import saberliou.demo.profile.GithubUser
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.data.Result.Companion.isSuccess
import saberliou.demo.profile.data.Result.Error
import saberliou.demo.profile.data.Result.Success
import saberliou.demo.profile.data.source.GithubUserDataSource.Companion.GITHUB_USER_NOT_FOUND
import javax.inject.Inject

class FakeGithubRepository @Inject constructor() : IGithubRepository {
    private lateinit var githubUser: Result<GithubUser>
    private val observableGithubUser = MutableLiveData<Result<GithubUser>>()
    private var shouldReturnError = false

    override suspend fun setGithubUser(githubUser: GithubUser) {
        this.githubUser = Success(githubUser)
        refreshGithubUser()
    }

    override suspend fun getGithubUser(forceUpdate: Boolean): Result<GithubUser> {
        return if (githubUser.isSuccess()) githubUser else Error(Exception(GITHUB_USER_NOT_FOUND))
    }

    override fun observeGithubUser(): LiveData<Result<GithubUser>> {
        return observableGithubUser
    }

    override suspend fun refreshGithubUser() {
        observableGithubUser.value = getGithubUser()
    }

    override suspend fun clearGithubUser() {
        TODO("Not yet implemented")
    }

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }
}
