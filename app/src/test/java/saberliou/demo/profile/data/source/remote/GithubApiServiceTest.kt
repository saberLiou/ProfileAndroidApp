package saberliou.demo.profile.data.source.remote

import androidx.test.filters.SmallTest
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@SmallTest
class GithubApiServiceTest {
    private lateinit var githubApiService: GithubApiService

    @get:Rule
    var mockWebServerRule = MockWebServerRule {
        githubApiService = it.create(GithubApiService::class.java)
    }

    @Test
    fun getRemoteGithubUser() {
        // GIVEN
        val responseJsonFile = "saberLiou.json"
        val expected = Gson().fromJson(
            mockWebServerRule.getResponseJsonString(responseJsonFile),
            RemoteGithubUser::class.java
        )
        mockWebServerRule.enqueueResponse(responseJsonFile)

        // WHEN
        val actual = runBlocking { githubApiService.getUser() }

        // THEN
        assertEquals(GithubApiUrls.GET_USER, mockWebServerRule.takeRequest().path)
        assertEquals(expected, actual)
    }
}
