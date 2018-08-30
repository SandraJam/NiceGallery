package com.sandra.dupre.business.detail

import com.sandra.dupre.business.PictureNotExistException

interface DetailInteractor {
    fun pickPicture(id: Int)
}

class DetailInteractorImpl(
        private val repository: DetailRepository,
        private val presenter: DetailPresenter
): DetailInteractor {
    override fun pickPicture(id: Int) {
        try {
            presenter.presentFullScreenPicture(repository.getHDPicture(id).fullUrl)
        } catch (e: PictureNotExistException) {
            presenter.presentError()
        }
    }
}