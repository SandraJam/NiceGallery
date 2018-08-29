package com.sandra.dupre.nicegallery.gallery

import com.sandra.dupre.business.gallery.GalleryPresenter
import com.sandra.dupre.business.gallery.PreviewPicture
import com.sandra.dupre.nicegallery.gallery.view.GalleryView
import com.sandra.dupre.nicegallery.gallery.view.PictureViewModel

class GalleryPresenterImpl(private val view: GalleryView): GalleryPresenter {
    override fun presentPictures(pictures: List<PreviewPicture>) {
        view.displayPictures(pictures.map {
            PictureViewModel(
                    id = it.id,
                    previewUrl = it.url
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