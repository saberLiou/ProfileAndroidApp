package saberliou.demo.profile.data.source.remote

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MockWebServerRule(val afterRetrofitCreated: (Retrofit) -> Unit) : TestWatcher() {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var retrofit: Retrofit

    override fun starting(description: Description?) {
        super.starting(description)
        mockWebServer = MockWebServer()
        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        afterRetrofitCreated(retrofit)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        mockWebServer.shutdown()
    }

    fun getResponseJsonString(fileName: String): String = javaClass.classLoader!!
        .getResourceAsStream("api-response/$fileName").source().buffer()
        .readString(Charsets.UTF_8)

    fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val mockResponse = MockResponse()
        headers.forEach { (key, value) -> mockResponse.addHeader(key, value) }
        mockWebServer.enqueue(mockResponse.setBody(getResponseJsonString(fileName)))
    }

    fun takeRequest(): RecordedRequest {
        return mockWebServer.takeRequest()
    }
}
