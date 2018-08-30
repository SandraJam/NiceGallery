package com.sandra.dupre.business.gallery

import com.sandra.dupre.business.NetworkException
import com.sandra.dupre.business.NoOtherPageException

interface GalleryRepository {
    @Throws(NetworkException::class, NoOtherPageException::class)
    fun loadPictures(page: Int): List<Picture>
}