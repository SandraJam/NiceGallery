package com.sandra.dupre.nicegallery.gallery

import android.content.res.Resources
import com.sandra.dupre.business.gallery.Color
import com.sandra.dupre.business.gallery.Picture
import com.sandra.dupre.nicegallery.R
import com.sandra.dupre.nicegallery.gallery.view.GalleryView
import com.sandra.dupre.nicegallery.gallery.view.PreviewPictureViewModel
import org.junit.Before

import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GalleryPresenterImplTest {
    @Mock
    private lateinit var view: GalleryView
    @Mock
    private lateinit var resources: Resources

    @InjectMocks
    private lateinit var presenter: GalleryPresenterImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun presentPictures_WhenNormalCase_ShouldCallDisplayPictures() {
        presenter.presentPictures(listOf(
                Picture(1, "url1", "fullUrl1"),
                Picture(2, "url2", "fullUr2")
        ))

        then(view).should(only()).displayPictures(listOf(
                PreviewPictureViewModel(1, "url1"),
                PreviewPictureViewModel(2, "url2")
        ))
    }

    @Test
    fun presentError_WhenNormalCase_ShouldCallDisplayError() {
        presenter.presentError()

        then(view).should(only()).displayError()
    }

    @Test
    fun presentNoMore_WhenNormalCase_ShouldCallStopLoadPicture() {
        presenter.presentNoMoreLoad()

        then(view).should(only()).stopLoadPictures()
    }

    @Test
    fun presentColors_WhenNormalCase_ShouldCallDisplayColor() {
        given(resources.getString(R.string.red)).willReturn("red")
        given(resources.getString(R.string.pink)).willReturn("pink")

        presenter.presentColors(listOf(Color.RED, Color.PINK))

        then(view).should(only()).displayColorBottom(listOf(
                Pair(R.color.redColor, "red"),
                Pair(R.color.pinkColor, "pink")
        ))
    }
}