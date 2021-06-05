package com.tmdb.app.data.base

import androidx.lifecycle.MediatorLiveData

/**
 * TMDBMediatorLiveData extends MediatorLiveData
 * Which we gives us call back whether the viewcycle observer active or inactive
 */
class TMDBMediatorLiveData<T>(
    private val onActiveListener: OnActiveListener
) : MediatorLiveData<T>() {

    constructor(onActive: () -> Unit = {}, onInactive: () -> Unit = {}) : this(
        onActiveListenerFromLambdas(onActive, onInactive)
    )

    override fun onActive() {
        super.onActive()
        onActiveListener.onActive()
    }

    override fun onInactive() {
        super.onInactive()
        onActiveListener.onInactive()
    }
}

fun onActiveListenerFromLambdas(
    onActive: () -> Unit,
    onInactive: () -> Unit
): OnActiveListener {

    return object : OnActiveListener {
        override fun onActive() {
            onActive.invoke()
        }

        override fun onInactive() {
            onInactive.invoke()
        }
    }
}

interface OnActiveListener {
    fun onActive()
    fun onInactive()
}