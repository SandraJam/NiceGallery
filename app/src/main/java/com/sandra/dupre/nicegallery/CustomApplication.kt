package com.sandra.dupre.nicegallery

import android.app.Application

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MainDependencies.init(this)
    }
}