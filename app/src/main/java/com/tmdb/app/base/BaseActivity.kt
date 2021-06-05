package com.tmdb.app.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tmdb.AppAlerts
import com.tmdb.app.AppCache
import com.tmdb.app.R
import com.tmdb.app.interfaces.OnDialogButton
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker
import com.treebo.internetavailabilitychecker.InternetConnectivityListener

abstract class BaseActivity : AppCompatActivity(), InternetConnectivityListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        InternetAvailabilityChecker.getInstance().addInternetConnectivityListener(this)
    }

    abstract fun getLayoutId(): Int

    override fun onInternetConnectivityChanged(isConnected: Boolean) {
        if (!isConnected) {
            AppAlerts.showResponseDialog(this,
                title = this.getString(R.string.internet_error_title),
                msg = this.getString(R.string.internet_error),
                onDialogButton = object : OnDialogButton {
                    override fun onOk() {
                       finish()
                    }
                },
                positiveText = R.string.exit,
                isCancelable = false
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppCache.clear()
        InternetAvailabilityChecker.getInstance().removeAllInternetConnectivityChangeListeners()
    }

}