package com.sandra.dupre.business.gallery

import com.sandra.dupre.business.NetworkException
import com.sandra.dupre.business.NoOtherPageException
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
                Picture(1, "url1", "fullUrl1"),
                Picture(2, "url2", "fullUrl2")
        )

        given(repository.loadPictures()).willReturn(pictures)

        interactor.findPictures()

        then(repository).should().loadNextPictures()
        then(presenter).should(only()).presentPictures(pictures)
    }

    @Test
    fun findPictures_WhenRepositoryThrowsNetworkException_ShouldPresentError() {
        given(repository.loadNextPictures()).willThrow(NetworkException())

        interactor.findPictures()

        then(presenter).should(only()).presentError()
    }

    @Test
    fun findPictures_WhenRepositoryThrowsNoOtherPageException_ShouldPresentNoMoreLoad() {
        given(repository.loadNextPictures()).willThrow(NoOtherPageException())

        interactor.findPictures()

        then(presenter).should(only()).presentNoMoreLoad()
    }
}