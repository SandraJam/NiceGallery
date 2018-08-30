package com.sandra.dupre.business.gallery

interface GalleryPresenter {
    fun presentPictures(pictures: List<Picture>)
    fun presentError()
    fun presentNoMoreLoad()
}