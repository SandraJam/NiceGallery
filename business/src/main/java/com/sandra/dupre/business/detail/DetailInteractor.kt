package com.sandra.dupre.business.detail

import com.sandra.dupre.business.PictureNotExistException

interface DetailInteractor {
    fun pickPicture(id: Int)
}

class DetailInteractorImpl(
        private val repository: DetailRepository,
        private val presenter: DetailPresenter
) : DetailInteractor {
    override fun pickPicture(id: Int) {
        try {
            repository.getHDAllPictures().let {
                presenter.presentFullScreenPicture(
                        it.map { it.fullUrl },
                        it.indexOf(repository.getHDPicture(id))
                )
            }
        } catch (e: PictureNotExistException) {
            presenter.presentError()
        }
    }
}