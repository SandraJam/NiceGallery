package com.sandra.dupre.nicegallery.detail

import com.sandra.dupre.business.detail.DetailInteractor
import com.sandra.dupre.business.detail.DetailInteractorImpl
import com.sandra.dupre.business.detail.DetailPresenter
import com.sandra.dupre.business.detail.DetailRepository
import com.sandra.dupre.nicegallery.ActivityScope
import com.sandra.dupre.nicegallery.MainComponent
import com.sandra.dupre.nicegallery.detail.view.DetailActivity
import com.sandra.dupre.nicegallery.detail.view.DetailView
import com.sandra.dupre.repository.DataSource
import com.sandra.dupre.repository.detail.DetailRepositoryImpl
import com.sandra.dupre.repository.pixabay.PicturePixabayEntity
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class DetailModule(private val view: DetailView) {

    @Provides
    fun provideDetailPresenter(): DetailPresenter = DetailPresenterImpl(view)

    @Provides
    fun provideDetailInteractor(
            repository: DetailRepository,
            presenter: DetailPresenter
    ): DetailInteractor = DetailInteractorImpl(
            repository,
            presenter
    )

    @Provides
    fun provideDetailRepository(
            dataSource: DataSource<List<PicturePixabayEntity>>
    ): DetailRepository = DetailRepositoryImpl(dataSource)
}

@ActivityScope
@Component(
        dependencies = [(MainComponent::class)],
        modules = [(DetailModule::class)]
)
interface DetailComponent {
    fun inject(activity: DetailActivity)
}