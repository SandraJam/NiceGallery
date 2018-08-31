package com.sandra.dupre.business.detail

import com.sandra.dupre.business.gallery.Picture

interface DetailRepository {
    fun getHDAllPictures(): List<Picture>
}