package com.sandra.dupre.repository.gallery

import com.sandra.dupre.business.gallery.GalleryRepository
import com.sandra.dupre.business.gallery.PreviewPicture
import com.sandra.dupre.repository.DataSource
import com.sandra.dupre.repository.pixabay.PicturePixabayEntity

class GalleryRepositoryImpl(
        private val dataSource: DataSource<List<PicturePixabayEntity>>
) : GalleryRepository {
    override fun loadPictures(page: Int): List<PreviewPicture> =
        (1..page)
                .map { dataSource.get(it) }
                .flatten()
                .map {
                    PreviewPicture(it.id, it.previewURL)
                }
}