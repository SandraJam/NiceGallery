package com.sandra.dupre.business.detail

import com.sandra.dupre.business.PictureNotExistException
import com.sandra.dupre.business.gallery.Picture

interface DetailRepository {
    @Throws(PictureNotExistException::class)
    fun getHDPicture(id: Int): Picture
}