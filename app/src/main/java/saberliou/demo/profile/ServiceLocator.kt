package saberliou.demo.profile

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import saberliou.demo.profile.data.source.IGithubRepository
import saberliou.demo.profile.data.source.local.AppDatabase

object ServiceLocator {
    private const val DATABASE_NAME = "app-database"
    private var database: AppDatabase? = null

    @Volatile
    var githubRepository: IGithubRepository? = null
        @VisibleForTesting set

    private fun getAppDatabase(context: Context) =
        database ?: Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
            .also { database = it }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(Any()) {
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            githubRepository = null
        }
    }

//    fun provideGithubRepository(context: Context) = githubRepository ?: synchronized(this) {
//        githubRepository ?: GithubRepository(
//            GithubUserRemoteDataSource(),
//            GithubUserLocalDataSource(getAppDatabase(context).githubUserDao)
//        ).also { githubRepository = it }
//    }
}
