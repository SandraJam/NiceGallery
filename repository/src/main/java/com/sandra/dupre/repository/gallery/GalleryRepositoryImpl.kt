package com.sandra.dupre.repository.gallery

import com.sandra.dupre.business.gallery.GalleryRepository
import com.sandra.dupre.business.gallery.PreviewPicture
import com.sandra.dupre.repository.DataSource
import com.sandra.dupre.repository.pixabay.PicturePixabayEntity

class GalleryRepositoryImpl(
        private val dataSource: DataSource<List<PicturePixabayEntity>>
): GalleryRepository {
    override fun loadPictures(): List<PreviewPicture> {
        val get = dataSource.get()
        return get.map {
            PreviewPicture(it.id, it.previewURL)
        }
    }
}