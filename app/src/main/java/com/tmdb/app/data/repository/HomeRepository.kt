package com.tmdb.app.data.repository

import androidx.lifecycle.LiveData
import com.tmdb.app.AppConfig
import com.tmdb.app.data.api.APIs
import com.tmdb.app.data.base.GenericRequestHandler
import com.tmdb.app.data.models.Envelope
import okhttp3.ResponseBody
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apIs: APIs) {

    fun getMovies(page: Int): LiveData<Envelope<String>>? {
        return GenericRequestHandler<ResponseBody>().doRequest(apIs.getMovies(AppConfig.API_KEY, page))
    }

    fun getGenres(): LiveData<Envelope<String>>? {
        return GenericRequestHandler<ResponseBody>().doRequest(apIs.getGenres(AppConfig.API_KEY))
    }
}