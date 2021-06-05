package com.tmdb.app.data.models


interface UIResultListener<T> {

    fun onLoading() {}
    fun onReady(data:T)
    fun onError()

}