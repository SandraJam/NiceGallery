package com.sandra.dupre.business.gallery

import com.sandra.dupre.business.NetworkException

interface GalleryInteractor {
    fun findPictures()
}

class GalleryInteractorImpl(
    private val repository: GalleryRepository,
    private val presenter: GalleryPresenter
): GalleryInteractor {
    override fun findPictures() {
        try {
            presenter.presentPictures(repository.loadPictures())
        } catch (e: NetworkException) {
            presenter.presentError()
        }
    }
}