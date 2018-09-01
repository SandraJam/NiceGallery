package com.sandra.dupre.nicegallery.gallery

import android.content.res.Resources
import com.sandra.dupre.business.gallery.GalleryInteractor
import com.sandra.dupre.business.gallery.GalleryInteractorImpl
import com.sandra.dupre.business.gallery.GalleryPresenter
import com.sandra.dupre.business.gallery.GalleryRepository
import com.sandra.dupre.nicegallery.ActivityScope
import com.sandra.dupre.nicegallery.MainComponent
import com.sandra.dupre.nicegallery.gallery.view.GalleryActivity
import com.sandra.dupre.nicegallery.gallery.view.GalleryView
import com.sandra.dupre.repository.DataSource
import com.sandra.dupre.repository.gallery.GalleryRepositoryImpl
import com.sandra.dupre.repository.pixabay.PicturePixabayEntity
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class GalleryModule(private val view: GalleryView) {

    @Provides
    fun provideGalleryPresenter(
            resources: Resources
    ): GalleryPresenter = GalleryPresenterImpl(view, resources)

    @Provides
    fun provideGalleryInteractor(
            repository: GalleryRepository,
            presenter: GalleryPresenter
    ): GalleryInteractor = GalleryInteractorImpl(
            repository,
            presenter
    )

    @Provides
    fun provideGalleryRepository(
            dataSource: DataSource<List<PicturePixabayEntity>>
    ): GalleryRepository = GalleryRepositoryImpl(dataSource)
}

@ActivityScope
@Component(
        dependencies = [(MainComponent::class)],
        modules = [(GalleryModule::class)]
)
interface GalleryComponent {
    fun inject(activity: GalleryActivity)
}