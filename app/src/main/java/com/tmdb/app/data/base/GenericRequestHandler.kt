package com.tmdb.app.data.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tmdb.app.data.models.Envelope
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GenericRequestHandler<R> {

    fun doRequest(makeRequest: Call<R>, showLoading:Boolean = true): LiveData<Envelope<String>> {

        //if (showLoading) eventBus.post(AppEvents.StartLoading.œ())

        val liveData = MutableLiveData<Envelope<String>>()

        makeRequest.enqueue(object : ApiCallback<R>() {

            override fun handleResponseData(data: Envelope<String>?) {
                liveData.value = data
            }

        })
        return liveData
    }

}
