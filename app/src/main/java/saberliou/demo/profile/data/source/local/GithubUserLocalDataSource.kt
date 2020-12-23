package saberliou.demo.profile.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import saberliou.demo.profile.GithubUser
import saberliou.demo.profile.GithubUser.Companion.toDatabaseEntity
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.data.source.GithubUserDataSource
import saberliou.demo.profile.data.source.GithubUserDataSource.Companion.GITHUB_USER_NOT_FOUND
import saberliou.demo.profile.data.source.local.GithubUserEntity.Companion.toDomainModel

class GithubUserLocalDataSource(
    private val githubUserDao: GithubUserDao,
    private val ioDispatcher: CoroutineDispatcher
) : GithubUserDataSource {
    override suspend fun setGithubUser(githubUser: GithubUser) {
        withContext(ioDispatcher) {
            githubUserDao.insert(githubUser.toDatabaseEntity())
        }
    }

    override suspend fun getGithubUser(): Result<GithubUser> = withContext(ioDispatcher) {
        return@withContext try {
            val githubUserEntity = githubUserDao.getLatest()
            if (githubUserEntity != null) {
                Result.Success(githubUserEntity.toDomainModel())
            } else {
                throw Exception(GITHUB_USER_NOT_FOUND)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun observeGithubUser(): LiveData<Result<GithubUser>> =
        githubUserDao.observeLatest().map {
            Result.Success(it.toDomainModel())
        }

    override suspend fun refreshGithubUser() {
        TODO("Not yet implemented")
    }

    override suspend fun clearGithubUser() {
        withContext(ioDispatcher) {
            githubUserDao.deleteAll()
        }
    }
}
