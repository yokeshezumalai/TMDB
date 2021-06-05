package com.tmdb.app.features.dashboard

import com.tmdb.app.R
import com.tmdb.app.base.BaseActivity
import com.treebo.internetavailabilitychecker.InternetConnectivityListener
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class MainActivity : BaseActivity(), InternetConnectivityListener, HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}