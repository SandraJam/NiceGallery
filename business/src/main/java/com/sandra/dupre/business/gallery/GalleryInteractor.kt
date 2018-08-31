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

    override fun findPictures() {
        try {
            repository.loadNextPictures()
            presenter.presentPictures(repository.loadPictures())
        } catch (e: NetworkException) {
            presenter.presentError()
        } catch (e: NoOtherPageException) {
            presenter.presentNoMoreLoad()
        }
    }

}