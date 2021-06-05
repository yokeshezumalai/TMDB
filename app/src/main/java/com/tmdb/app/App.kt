package com.tmdb.app

import androidx.multidex.MultiDexApplication
import com.tmdb.app.di.AppInjector
import com.tmdb.app.di.component.AppComponent
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class App : MultiDexApplication(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        initializeDagger()
        InternetAvailabilityChecker.init(this);

        eventBus = EventBus.getDefault()

        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
            .methodCount(0)         // (Optional) How many method line to show. Default 2
            .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
            .tag("tmdb")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

    private fun initializeDagger() {
        appComponent = AppInjector.init(this)

    }

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var eventBus: EventBus
    }

}