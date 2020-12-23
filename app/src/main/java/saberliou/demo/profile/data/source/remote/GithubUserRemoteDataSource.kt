package saberliou.demo.profile.data.source.remote

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import saberliou.demo.profile.GithubUser
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.data.source.GithubUserDataSource
import saberliou.demo.profile.data.source.remote.RemoteGithubUser.Companion.toDomainModel

class GithubUserRemoteDataSource(
    private val githubApiService: GithubApiService,
    private val ioDispatcher: CoroutineDispatcher
) : GithubUserDataSource {
//    private val githubApiService = RetrofitClient.githubApiService

    override suspend fun setGithubUser(githubUser: GithubUser) {
        TODO("Not yet implemented")
    }

    override suspend fun getGithubUser(): Result<GithubUser> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(githubApiService.getUser().toDomainModel())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun observeGithubUser(): LiveData<Result<GithubUser>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshGithubUser() {
        TODO("Not yet implemented")
    }

    override suspend fun clearGithubUser() {
        TODO("Not yet implemented")
    }
}
