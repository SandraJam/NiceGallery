package com.sandra.dupre.business.gallery

import com.sandra.dupre.business.NetworkException
import org.junit.Before

import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GalleryInteractorImplTest {
    @Mock
    lateinit var repository: GalleryRepository
    @Mock
    lateinit var presenter: GalleryPresenter

    @InjectMocks
    lateinit var interactor: GalleryInteractorImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun findPictures_WhenRepositoryReturnPictures_ShouldPresentPictures() {
        val pictures = listOf(
                PreviewPicture("id1", "url1"),
                PreviewPicture("id2", "url2")
        )

        given(repository.loadPictures()).willReturn(pictures)

        interactor.findPictures()

        then(presenter).should(only()).presentPictures(pictures)

    }

    @Test
    fun findPictures_WhenRepositoryThrowsNetworkException_ShouldPresentError() {
        given(repository.loadPictures()).willThrow(NetworkException())

        interactor.findPictures()

        then(presenter).should(only()).presentError()
    }
}