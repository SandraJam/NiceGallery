package com.sandra.dupre.repository.detail

import com.sandra.dupre.business.PictureNotExistException
import com.sandra.dupre.business.detail.DetailRepository
import com.sandra.dupre.business.gallery.Picture
import com.sandra.dupre.repository.DataSource
import com.sandra.dupre.repository.pixabay.PicturePixabayEntity

class DetailRepositoryImpl(
        private val dataSource: DataSource<List<PicturePixabayEntity>>
): DetailRepository {
    override fun getHDPicture(id: Int): Picture =
        dataSource.getAll().find { it.id == id }?.let {
            Picture(
                    id = it.id,
                    previewUrl = it.previewURL,
                    fullUrl = it.largeImageURL
            )
        } ?: throw PictureNotExistException()
}