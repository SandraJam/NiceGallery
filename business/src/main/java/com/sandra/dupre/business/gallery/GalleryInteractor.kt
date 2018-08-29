package com.sandra.dupre.business.gallery

import com.sandra.dupre.business.NetworkException
import com.sandra.dupre.business.NoOtherPageException

interface GalleryInteractor {
    fun findPictures()
}

class GalleryInteractorImpl(
        private val repository: GalleryRepository,
        private val presenter: GalleryPresenter
) : GalleryInteractor {
    private var currentPage = 0

    override fun findPictures() {
        try {
            currentPage += 1
            presenter.presentPictures(repository.loadPictures(currentPage))
        } catch (e: NetworkException) {
            presenter.presentError()
        } catch (e: NoOtherPageException) {
            presenter.presentNoMoreLoad()
        }
    }
}