package com.sandra.dupre.repository.gallery

import com.sandra.dupre.business.gallery.GalleryRepository
import com.sandra.dupre.business.gallery.Picture
import com.sandra.dupre.repository.DataSource
import com.sandra.dupre.repository.pixabay.PicturePixabayEntity

class GalleryRepositoryImpl(
        private val dataSource: DataSource<List<PicturePixabayEntity>>
) : GalleryRepository {

    override fun loadPictures(): List<Picture> = dataSource.getAll()
            .map {
                Picture(
                        id = it.id,
                        previewUrl = it.previewURL,
                        fullUrl = it.largeImageURL
                )
            }

    override fun loadNextPictures(color: String?) = dataSource.loadNextPage(color)
}