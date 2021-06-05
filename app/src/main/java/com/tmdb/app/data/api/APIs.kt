package com.tmdb.app.data.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIs {

    @GET("movie/popular?")
    fun getMovies(@Query("api_key") apiKey: String?, @Query("page") page: Int?): Call<ResponseBody>

    @GET("genre/movie/list?")
    fun getGenres(@Query("api_key") apiKey: String?): Call<ResponseBody>
}