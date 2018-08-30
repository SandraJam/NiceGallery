package com.sandra.dupre.repository.gallery

import com.sandra.dupre.business.gallery.GalleryRepository
import com.sandra.dupre.business.gallery.Picture
import com.sandra.dupre.repository.DataSource
import com.sandra.dupre.repository.pixabay.PicturePixabayEntity

class GalleryRepositoryImpl(
        private val dataSource: DataSource<List<PicturePixabayEntity>>
) : GalleryRepository {
    override fun loadPictures(page: Int): List<Picture> =
        (1..page)
                .map { dataSource.get(it) }
                .flatten()
                .map {
                    Picture(it.id, it.previewURL, it.largeImageURL)
                }
}