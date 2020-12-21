package saberliou.demo.profile.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import saberliou.demo.profile.GithubUser
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.data.Result.Error
import saberliou.demo.profile.data.Result.Success
import saberliou.demo.profile.data.source.GithubUserDataSource.Companion.GITHUB_USER_NOT_FOUND

class FakeGithubUserDataSource : GithubUserDataSource {
    private var githubUser: GithubUser? = null
    private val observableGithubUser = MutableLiveData<Result<GithubUser>>()

    override suspend fun setGithubUser(githubUser: GithubUser) {
        this.githubUser = githubUser
        refreshGithubUser()
    }

    override suspend fun getGithubUser(): Result<GithubUser> {
        githubUser?.let { return Success(it) }
        return Error(Exception(GITHUB_USER_NOT_FOUND))
    }

    override suspend fun refreshGithubUser() {
        observableGithubUser.value = getGithubUser()
    }

    override fun observeGithubUser(): LiveData<Result<GithubUser>> = observableGithubUser

    override suspend fun clearGithubUser() {
        githubUser = null
        refreshGithubUser()
    }
}
