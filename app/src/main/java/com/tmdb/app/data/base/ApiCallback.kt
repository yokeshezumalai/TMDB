package com.tmdb.app.data.base

import com.tmdb.app.data.models.Envelope
import com.google.gson.GsonBuilder
import com.orhanobut.logger.Logger
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class ApiCallback<T> : Callback<T> {

    override fun onResponse(call: Call<T>?, response: Response<T>?) =
        if (response != null && response.isSuccessful && response.body() != null) {
            val responseBody = (response.body() as ResponseBody).string()
            val envelope = Envelope<String>()
            envelope.data = responseBody
            handleResponseData(envelope)
        } else {
            handleResponseData(null)
        }

    override fun onFailure(call: Call<T>, t: Throwable) {
        handleResponseData(null)
    }

    protected abstract fun handleResponseData(data: Envelope<String>?)



}

