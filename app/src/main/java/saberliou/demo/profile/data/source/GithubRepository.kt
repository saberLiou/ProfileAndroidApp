package saberliou.demo.profile.data.source

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import saberliou.demo.profile.GithubUser
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.util.wrapEspressoIdlingResource

interface IGithubRepository {
    suspend fun setGithubUser(githubUser: GithubUser)
    suspend fun getGithubUser(forceUpdate: Boolean = false): Result<GithubUser>
    fun observeGithubUser(): LiveData<Result<GithubUser>>
    suspend fun refreshGithubUser()
    suspend fun clearGithubUser()
}

class GithubRepository(
    private val remoteDataSource: GithubUserDataSource,
    private val localDataSource: GithubUserDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : IGithubRepository {
    override suspend fun setGithubUser(githubUser: GithubUser) {
        wrapEspressoIdlingResource {
            withContext(ioDispatcher) {
                localDataSource.setGithubUser(githubUser)
            }
        }
    }

    override suspend fun getGithubUser(forceUpdate: Boolean): Result<GithubUser> {
        wrapEspressoIdlingResource {
            return withContext(ioDispatcher) {
                if (forceUpdate) {
                    try {
                        refreshGithubUser()
                    } catch (e: Exception) {
                        return@withContext Result.Error(e)
                    }
                }

                return@withContext localDataSource.getGithubUser()
            }
        }
    }

    override fun observeGithubUser(): LiveData<Result<GithubUser>> {
        wrapEspressoIdlingResource {
            return localDataSource.observeGithubUser()
        }
    }

    override suspend fun refreshGithubUser() {
        wrapEspressoIdlingResource {
            withContext(ioDispatcher) {
                val remoteGithubUser = remoteDataSource.getGithubUser()

                if (remoteGithubUser is Result.Success) {
                    localDataSource.setGithubUser(remoteGithubUser.data.copy(id = 0))
                } else if (remoteGithubUser is Result.Error) {
                    throw remoteGithubUser.exception
                }
            }
        }
    }

    override suspend fun clearGithubUser() {
        wrapEspressoIdlingResource {
            withContext(ioDispatcher) {
                localDataSource.clearGithubUser()
            }
        }
    }
}
