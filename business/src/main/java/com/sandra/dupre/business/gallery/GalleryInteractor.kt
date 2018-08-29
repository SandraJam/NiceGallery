package com.sandra.dupre.business.gallery

interface GalleryInteractor {

}

class GalleryInteractorImpl(
    private val repository: GalleryRepository,
    private val presenter: GalleryPresenter
): GalleryInteractor {

}