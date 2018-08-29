package com.sandra.dupre.nicegallery.gallery

import com.sandra.dupre.business.gallery.PreviewPicture
import com.sandra.dupre.nicegallery.gallery.view.GalleryView
import com.sandra.dupre.nicegallery.gallery.view.PictureViewModel
import org.junit.Before

import org.junit.Test
import org.mockito.BDDMockito.only
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GalleryPresenterImplTest {
    @Mock
    lateinit var view: GalleryView

    @InjectMocks
    lateinit var presenter: GalleryPresenterImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun presentPictures_WhenNormalCase_ShouldCallDisplayPictures() {
        presenter.presentPictures(listOf(
                PreviewPicture(1, "url1"),
                PreviewPicture(2, "url2")
        ))

        then(view).should(only()).displayPictures(listOf(
                PictureViewModel(1, "url1"),
                PictureViewModel(2, "url2")
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
}