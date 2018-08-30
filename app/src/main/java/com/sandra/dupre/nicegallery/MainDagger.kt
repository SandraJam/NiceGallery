package com.sandra.dupre.nicegallery

import android.content.Context
import android.content.res.Resources
import com.sandra.dupre.repository.DataSource
import com.sandra.dupre.repository.pixabay.PicturePixabayEntity
import com.sandra.dupre.repository.pixabay.PixabayDataSource
import com.sandra.dupre.repository.pixabay.PixabayServices
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class MainModule(private val context: Context) {

    @Singleton
    @Provides
    fun providesResources() : Resources = context.resources

    @Singleton
    @Provides
    fun providePixabayDataSource(): DataSource<List<PicturePixabayEntity>> = PixabayDataSource(
            Retrofit.Builder()
                    .baseUrl("https://pixabay.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PixabayServices::class.java)
    )

}

@Singleton
@Component(modules = [(MainModule::class)])
interface MainComponent {
    fun resources(): Resources

    fun pixabayDataSource(): DataSource<List<PicturePixabayEntity>>
}