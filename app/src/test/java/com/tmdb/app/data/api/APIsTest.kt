package com.tmdb.app.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tmdb.app.AppConfig
import com.tmdb.app.data.models.GenreDetails
import com.tmdb.app.data.models.Results
import com.tmdb.app.util.RetrofitTestUtil.getResponse
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.json.JSONObject
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class APIsTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: APIs

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIs::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetMovies() {
        enqueueResponse("get_movies.json")
        val responseBody = (getResponse(call = service.getMovies(AppConfig.API_KEY, 1)))?.data

        responseBody?.let {
            val mainObject = JSONObject(responseBody)
            val contents = Results().getMovies(mainObject.getJSONArray("results").toString())

            assertThat(contents, notNullValue())
            Assert.assertFalse("List is empty or null", responseBody.isNullOrEmpty())

            contents?.forEach {
                assertThat(it.title, notNullValue())
                assertThat(it.overview, notNullValue())
                assertThat(it.id, notNullValue())
                assertThat(it.originalLanguage, notNullValue())
                assertThat(it.originalTitle, notNullValue())
                assertThat(it.posterPath, notNullValue())
                assertThat(it.vote, notNullValue())
            }
        }
    }

    @Test
    fun testGetGenres() {
        enqueueResponse("get_genres.json")
        val responseBody = (getResponse(call = service.getGenres(AppConfig.API_KEY)))?.data

        responseBody?.let {
            val mainObject = JSONObject(responseBody)
            val genres = GenreDetails().getGenres(mainObject.getJSONArray("genres").toString())

            assertThat(genres, notNullValue())
            Assert.assertFalse("List is empty or null", responseBody.isNullOrEmpty())

            genres?.forEach {
                assertThat(it.id, notNullValue())
                assertThat(it.name, notNullValue())
                assertThat(it.id, notNullValue())
            }
        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
            ?.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream!!))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockResponse.setResponseCode(200)
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }

}