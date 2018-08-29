package com.sandra.dupre.business.gallery

interface GalleryInteractor {
    fun findPictures()
}

class GalleryInteractorImpl(
    private val repository: GalleryRepository,
    private val presenter: GalleryPresenter
): GalleryInteractor {
    override fun findPictures() {

    }
}