package com.sandra.dupre.nicegallery

import android.content.Context

class MainDependencies(val mainComponent: MainComponent) {
    companion object {
        lateinit var instance: MainDependencies

        fun init(context: Context) {
            instance = MainDependencies(DaggerMainComponent.builder().mainModule(MainModule(context)).build())
        }
    }
}