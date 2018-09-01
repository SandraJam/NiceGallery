package com.sandra.dupre.repository.pixabay

import com.sandra.dupre.business.NetworkException
import com.sandra.dupre.business.NoOtherPageException
import com.sandra.dupre.repository.DataSource

class PixabayDataSource(
        private val retrofitServices: PixabayServices
) : DataSource<List<PicturePixabayEntity>> {

    companion object {
        private const val PER_PAGE = 40
        private const val KEY = "2952852-222a829dd5bea68bfc7fc69ec"
    }

    val pictures = mutableListOf<PicturePixabayEntity>()
    private var currentPage = 1
    private var hasAnotherPage = true
    private var color: String = ""

    override fun loadNextPage(param: String?) {
        if (param != null && color != param) {
            clearParameter()
            color = param
        }
        if (hasAnotherPage) {
            try {
                (retrofitServices.listPictures(KEY, currentPage, PER_PAGE, color)
                        .execute()
                        .body() ?: throw NetworkException())
                        .let {
                            pictures.addAll(it.hits)
                            hasAnotherPage = currentPage.times(PER_PAGE) < it.totalHits
                        }

                currentPage += 1
            } catch (e: Exception) {
                throw NetworkException()
            }
        } else {
            throw NoOtherPageException()
        }
    }

    override fun getAll(): List<PicturePixabayEntity> = pictures

    private fun clearParameter() {
        currentPage = 1
        pictures.clear()
        hasAnotherPage = true
    }
}