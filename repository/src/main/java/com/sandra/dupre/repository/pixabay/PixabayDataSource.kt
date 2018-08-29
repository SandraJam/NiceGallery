package com.sandra.dupre.repository.pixabay

import com.sandra.dupre.business.NetworkException
import com.sandra.dupre.business.NoOtherPageException
import com.sandra.dupre.repository.DataSource

class PixabayDataSource(
        private val retrofitServices: PixabayServices
) : DataSource<List<PicturePixabayEntity>> {

    companion object {
        private const val PER_PAGE = 32
    }

    var picturesMap: MutableMap<Int, PixabayEntity> = mutableMapOf()

    override fun get(page: Int): List<PicturePixabayEntity> =
            if (hasAnotherPage(page)) {
                try {
                    (picturesMap[page] ?: retrofitServices.listPictures(page, PER_PAGE)
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

    private fun hasAnotherPage(page: Int) =
            page.minus(1).times(PER_PAGE) < picturesMap[1]?.totalHits ?: Int.MAX_VALUE
}