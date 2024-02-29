package com.huar.log

import android.app.Application
import com.huar.log.ktx.AppExceptionHandler

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppExceptionHandler.init(this)
    }
}