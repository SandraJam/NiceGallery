package com.sandra.dupre.nicegallery.detail

import com.sandra.dupre.nicegallery.detail.view.DetailView
import org.junit.Before

import org.junit.Test
import org.mockito.BDDMockito.only
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailPresenterImplTest {
    @Mock
    private lateinit var view: DetailView

    @InjectMocks
    private lateinit var presenter: DetailPresenterImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun presentFullScreenPicture_WhenNormalCase_ShouldPresentUrl() {
        presenter.presentFullScreenPicture("previewUrl")

        then(view).should(only()).displayPicture("previewUrl")
    }

    @Test
    fun presentError_WhenHasError_ShouldFinishActivity() {
        presenter.presentError()

        then(view).should(only()).displayFinish()
    }
}