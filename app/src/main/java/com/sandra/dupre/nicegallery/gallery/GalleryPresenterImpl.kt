package com.sandra.dupre.nicegallery.gallery

import android.content.res.Resources
import com.sandra.dupre.business.gallery.Color
import com.sandra.dupre.business.gallery.GalleryPresenter
import com.sandra.dupre.business.gallery.Picture
import com.sandra.dupre.nicegallery.R
import com.sandra.dupre.nicegallery.gallery.view.GalleryView
import com.sandra.dupre.nicegallery.gallery.view.PreviewPictureViewModel

class GalleryPresenterImpl(
        private val view: GalleryView,
        private val resources: Resources
) : GalleryPresenter {
    override fun presentPictures(pictures: List<Picture>) {
        view.displayPictures(pictures.map {
            PreviewPictureViewModel(
                    id = it.id,
                    previewUrl = it.previewUrl
            )
        })
    }

    override fun presentError() {
        view.displayError()
    }

    override fun presentNoMoreLoad() {
        view.stopLoadPictures()
    }

    override fun presentColors(colors: List<Color>) {
        view.displayColorBottom(colors.map {
            Pair(getColorRes(it), getColorString(it))
        })
    }

    private fun getColorRes(color: Color) =
            when (color) {
                Color.NULL -> R.color.lightGray
                Color.RED -> R.color.redColor
                Color.ORANGE -> R.color.orangeColor
                Color.YELLOW -> R.color.yellowColor
                Color.GREEN -> R.color.greenColor
                Color.BLUE -> R.color.blueColor
                Color.LILAC -> R.color.lilacColor
                Color.PINK -> R.color.pinkColor
                Color.BROWN -> R.color.brownColor
            }

    private fun getColorString(color: Color) =
            resources.getString(
                    when (color) {
                        Color.NULL -> R.string.empty
                        Color.RED -> R.string.red
                        Color.ORANGE -> R.string.orange
                        Color.YELLOW -> R.string.yellow
                        Color.GREEN -> R.string.green
                        Color.BLUE -> R.string.blue
                        Color.LILAC -> R.string.lilac
                        Color.PINK -> R.string.pink
                        Color.BROWN -> R.string.brown
                    }
            )
}