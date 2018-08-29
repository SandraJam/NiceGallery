package com.sandra.dupre.business.gallery

import com.sandra.dupre.business.NetworkException

interface GalleryRepository {
    @Throws(NetworkException::class)
    fun loadPictures(): List<PreviewPicture>
}