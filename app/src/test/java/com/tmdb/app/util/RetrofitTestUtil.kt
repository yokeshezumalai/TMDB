package com.tmdb.app.util

import androidx.lifecycle.LiveData
import com.tmdb.app.data.base.GenericRequestHandler
import com.tmdb.app.data.models.Envelope
import com.tmdb.app.util.LiveDataTestUtil.getValue
import okhttp3.ResponseBody
import retrofit2.Call

object RetrofitTestUtil {
    fun <T> getResponse(call: Call<T>): Envelope<String>? {
        return getValue(GenericRequestHandler<T>().doRequest(call, false))
    }
}