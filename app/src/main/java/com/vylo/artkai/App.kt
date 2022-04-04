package com.vylo.artkai

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.vylo.artkai.presentation.StartActivity
import com.vylo.auth.signinfragment.domain.modul.authModule
import com.vylo.common.BaseActivity
import com.vylo.common.api.ApiBase
import com.vylo.common.domain.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application(), ContextProvider {

    private lateinit var authentication: String
    private lateinit var noInternet: String
    private var currentActivity: Activity? = null

    override fun onCreate() {
        super.onCreate()
        disableNightMode()
        registerBroadcast()
        registerBroadcastForNetwork()
        Config.init(this)
        ApiBase.updateBaseUrl(Config.BASE_URL, applicationContext)
        startKoin {
            androidContext(this@App)
            modules(listOf(
                commonModule,
                authModule
            ))
        }

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                currentActivity = activity
            }

            override fun onActivityStarted(activity: Activity) {
                currentActivity = activity
            }

            override fun onActivityResumed(activity: Activity) {
                currentActivity = activity
            }

            override fun onActivityPaused(activity: Activity) {
                currentActivity = null
            }

            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })

    }

    private fun returnToLoginScreen() {
        val logOut = Intent(this, StartActivity::class.java)
        logOut.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        this.startActivity(logOut)
    }

    private fun noInternetConnection() {
//        val activity = getActivityContext() as BaseActivity
//        activity.hideProgress()
//        activity.showNetworkErrorDialog()
    }

    private fun registerBroadcast() {
        authentication = applicationContext.getString(R.string.label_return_start_screen)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadCastReceiver, IntentFilter(authentication))
    }

    private fun registerBroadcastForNetwork() {
        noInternet = applicationContext.getString(R.string.label_no_connection)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadCastReceiver, IntentFilter(noInternet))
    }

    private fun unregisterBroadcast() {
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(broadCastReceiver)
    }

    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            when (intent?.action) {
                authentication -> returnToLoginScreen()
                noInternet -> noInternetConnection()
            }
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterBroadcast()
    }

    override fun getActivityContext(): Context? {
        return currentActivity
    }

    private fun disableNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}