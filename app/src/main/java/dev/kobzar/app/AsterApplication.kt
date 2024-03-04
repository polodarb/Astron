package dev.kobzar.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AsterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}