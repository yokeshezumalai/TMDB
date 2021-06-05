package com.tmdb.app.data.base

import androidx.lifecycle.Observer
import com.tmdb.app.data.models.Envelope


class ApiObserver<T>(private val changeListener: ChangeListener<T>) : Observer<Envelope<T>> {

    override fun onChanged(envelope: Envelope<T>?) {

        if (envelope?.data != null) {
            changeListener.onSuccess(envelope)

        } else {
            // general exception
            changeListener.onFailed()
        }
    }

    interface ChangeListener<T> {
        fun onSuccess(dataWrapper:Envelope<T>?)
        fun onFailed()
    }
}
