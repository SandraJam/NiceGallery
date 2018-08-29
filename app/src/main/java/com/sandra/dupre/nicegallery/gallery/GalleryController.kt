package com.sandra.dupre.nicegallery.gallery

import com.sandra.dupre.business.gallery.GalleryInteractor

interface GalleryController {

}

class GalleryControllerImpl(private val interactor: GalleryInteractor): GalleryController {

}