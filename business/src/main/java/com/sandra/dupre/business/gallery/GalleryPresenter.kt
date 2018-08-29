package com.sandra.dupre.business.gallery

interface GalleryPresenter {
    fun presentPictures(pictures: List<PreviewPicture>)
    fun presentError()
}