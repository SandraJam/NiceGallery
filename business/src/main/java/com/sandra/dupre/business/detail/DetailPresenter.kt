package com.sandra.dupre.business.detail

interface DetailPresenter {
    fun presentFullScreenPicture(urls: List<String>, position: Int)
    fun presentError()
}