package com.sandra.dupre.nicegallery.gallery

import com.sandra.dupre.business.gallery.GalleryPresenter
import com.sandra.dupre.business.gallery.Picture
import com.sandra.dupre.nicegallery.gallery.view.GalleryView
import com.sandra.dupre.nicegallery.gallery.view.PreviewPictureViewModel

class GalleryPresenterImpl(private val view: GalleryView): GalleryPresenter {
    override fun presentPictures(pictures: List<Picture>) {
        view.displayPictures(pictures.map {
            PreviewPictureViewModel(
                    id = it.id,
                    previewUrl = it.previewUrl
            )
        })
    }

    override fun presentError() {
        view.displayError()
    }

    override fun presentNoMoreLoad() {
        view.stopLoadPictures()
    }
}