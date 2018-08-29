package com.sandra.dupre.repository.pixabay

import com.sandra.dupre.business.NetworkException
import com.sandra.dupre.repository.DataSource

class PixabayDataSource(
        private val retrofitServices: PixabayServices
) : DataSource<List<PicturePixabayEntity>> {

    var picturesMap: MutableMap<Int, List<PicturePixabayEntity>> = mutableMapOf()

    override fun get(): List<PicturePixabayEntity> =
            try {
                picturesMap[1] ?: retrofitServices.listPictures()
                        .execute()
                        .body()
                        ?.hits
                        ?.also {
                            picturesMap[1] = it
                        } ?: throw NetworkException()
            } catch (e: Exception) {
                throw NetworkException()
            }
}