package com.sandra.dupre.business.gallery

import com.sandra.dupre.business.NetworkException
import com.sandra.dupre.business.NoOtherPageException

interface GalleryRepository {
    fun loadPictures(): List<Picture>
    @Throws(NetworkException::class, NoOtherPageException::class)
    fun loadNextPictures(color: String?)
}