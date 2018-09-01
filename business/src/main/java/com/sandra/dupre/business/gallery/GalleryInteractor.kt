package com.sandra.dupre.business.gallery

import com.sandra.dupre.business.NetworkException
import com.sandra.dupre.business.NoOtherPageException
import com.sandra.dupre.business.gallery.Color.*

interface GalleryInteractor {
    fun findPictures(color: String?)
    fun findColor()
}

class GalleryInteractorImpl(
        private val repository: GalleryRepository,
        private val presenter: GalleryPresenter
) : GalleryInteractor {

    override fun findPictures(color: String?) {
        try {
            repository.loadNextPictures(color)
            presenter.presentPictures(repository.loadPictures())
        } catch (e: NetworkException) {
            presenter.presentError()
        } catch (e: NoOtherPageException) {
            presenter.presentNoMoreLoad()
        }
    }

    override fun findColor() {
        presenter.presentColors(listOf(NULL, RED, ORANGE, YELLOW, GREEN, BLUE, LILAC, PINK, BROWN))
    }
}