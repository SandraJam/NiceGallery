package com.sandra.dupre.nicegallery.gallery

import com.sandra.dupre.business.gallery.GalleryInteractor
import com.sandra.dupre.business.gallery.GalleryInteractorImpl
import com.sandra.dupre.business.gallery.GalleryPresenter
import com.sandra.dupre.business.gallery.GalleryRepository
import com.sandra.dupre.nicegallery.ActivityScope
import com.sandra.dupre.nicegallery.MainComponent
import com.sandra.dupre.nicegallery.gallery.view.GalleryActivity
import com.sandra.dupre.nicegallery.gallery.view.GalleryView
import com.sandra.dupre.repository.gallery.GalleryRepositoryImpl
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class GalleryModule(private val view: GalleryView) {

    @Provides
    fun provideGalleryPresenter(): GalleryPresenter = GalleryPresenterImpl(view)

    @Provides
    fun provideGalleryInteractor(
            repository: GalleryRepository,
            presenter: GalleryPresenter
    ): GalleryInteractor = GalleryInteractorImpl(
            repository,
            presenter
    )

    @Provides
    fun provideGalleryRepository(): GalleryRepository = GalleryRepositoryImpl()
}

@ActivityScope
@Component(
        dependencies = [(MainComponent::class)],
        modules = [(GalleryModule::class)]
)
interface GalleryComponent {
    fun inject(activity: GalleryActivity)
}