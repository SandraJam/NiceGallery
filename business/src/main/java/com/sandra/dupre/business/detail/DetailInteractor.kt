package com.sandra.dupre.business.detail

interface DetailInteractor {
    fun pickPictures()
}

class DetailInteractorImpl(
        private val repository: DetailRepository,
        private val presenter: DetailPresenter
) : DetailInteractor {

    override fun pickPictures() {
        presenter.presentFullScreenPicture(repository.getHDAllPictures().map { it.fullUrl })
    }
}