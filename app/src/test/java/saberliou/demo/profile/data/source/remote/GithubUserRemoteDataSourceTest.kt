package saberliou.demo.profile.data.source.remote

import androidx.test.filters.SmallTest
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.data.source.remote.RemoteGithubUser.Companion.toDomainModel

@SmallTest
class GithubUserRemoteDataSourceTest {
    private lateinit var dataSource: GithubUserRemoteDataSource

    @get:Rule
    var mockWebServerRule = MockWebServerRule {
        dataSource = GithubUserRemoteDataSource(
            it.create(GithubApiService::class.java),
            Dispatchers.IO
        )
    }

    @Test
    fun getResult() {
        // GIVEN
        val responseJsonFile = "saberLiou.json"
        val expected = Result.Success(
            Gson().fromJson(
                mockWebServerRule.getResponseJsonString(responseJsonFile),
                RemoteGithubUser::class.java
            ).toDomainModel()
        )
        mockWebServerRule.enqueueResponse(responseJsonFile)

        // WHEN
        val actual = runBlocking { dataSource.getGithubUser() }

        // THEN
        assertEquals(expected, actual)
    }
}
