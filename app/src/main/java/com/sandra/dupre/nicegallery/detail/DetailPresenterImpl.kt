package com.sandra.dupre.nicegallery.detail

import com.sandra.dupre.business.detail.DetailPresenter
import com.sandra.dupre.nicegallery.detail.view.DetailView

class DetailPresenterImpl(private val view: DetailView): DetailPresenter {
    override fun presentFullScreenPicture(url: String) {
        view.displayPicture(url)
    }

    override fun presentError() {
        view.displayFinish()
    }
}