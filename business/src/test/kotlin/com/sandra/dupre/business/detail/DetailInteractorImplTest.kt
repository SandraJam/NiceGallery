package com.sandra.dupre.business.detail

import com.sandra.dupre.business.PictureNotExistException
import com.sandra.dupre.business.gallery.Picture
import org.junit.Before

import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.only
import org.mockito.MockitoAnnotations

class DetailInteractorImplTest {
    @Mock
    private lateinit var repository: DetailRepository
    @Mock
    private lateinit var presenter: DetailPresenter

    @InjectMocks
    private lateinit var interactor: DetailInteractorImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun pickPicture_WhenIdExist_ShouldCallPresenterWithUrl() {
        val picture = Picture(4, "previewUrl", "fullUrl")
        given(repository.getHDPicture(4)).willReturn(picture)
        given(repository.getHDAllPictures()).willReturn(listOf(picture))

        interactor.pickPicture(4)

        then(presenter).should(only()).presentFullScreenPicture(listOf("fullUrl"), 0)
    }

    @Test
    fun pickPicture_WhenIdNOTExist_ShouldCallPresenterError() {
        given(repository.getHDPicture(4)).willThrow(PictureNotExistException())

        interactor.pickPicture(4)

        then(presenter).should(only()).presentError()
    }
}