package com.tmdb.app.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Envelope<T> {

    var data:T? = null

    companion object {

        fun <T> toLiveData(data: T?): LiveData<Envelope<T>> {
            val liveData = MutableLiveData<Envelope<T>>()
            val envelope = Envelope<T>()
            envelope.data = data
            liveData.value = envelope
            return liveData
        }

        abstract class Property<T> {
            var value: T? = null
            abstract fun setValue(input: String?)
        }
    }
}