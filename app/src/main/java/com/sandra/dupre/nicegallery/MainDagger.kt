package com.sandra.dupre.nicegallery

import android.content.Context
import android.content.res.Resources
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModule(private val context: Context) {

    @Singleton
    @Provides
    fun providesResources() : Resources = context.resources

}

@Singleton
@Component(modules = [(MainModule::class)])
interface MainComponent {
    fun resources(): Resources
}