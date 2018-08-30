package com.sandra.dupre.repository.pixabay

import com.sandra.dupre.business.NetworkException
import com.sandra.dupre.business.NoOtherPageException
import com.sandra.dupre.repository.DataSource

class PixabayDataSource(
        private val retrofitServices: PixabayServices
) : DataSource<List<PicturePixabayEntity>> {

    companion object {
        private const val PER_PAGE = 32
        private const val KEY = "2952852-222a829dd5bea68bfc7fc69ec"
    }

    val picturesMap: MutableMap<Int, PixabayEntity> = mutableMapOf()

    override fun get(page: Int): List<PicturePixabayEntity> =
            if (hasAnotherPage(page)) {
                try {
                    (picturesMap[page] ?: retrofitServices.listPictures(KEY, page, PER_PAGE)
                            .execute()
                            .body()
                            ?.also {
                                picturesMap[page] = it
                            } ?: throw NetworkException()).hits
                } catch (e: Exception) {
                    throw NetworkException()
                }
            } else {
                throw NoOtherPageException()
            }

    override fun getAll(): List<PicturePixabayEntity> = picturesMap
            .map {
                it.value.hits
            }.flatten()

    private fun hasAnotherPage(page: Int) =
            page.minus(1).times(PER_PAGE) < picturesMap[1]?.totalHits ?: Int.MAX_VALUE
}