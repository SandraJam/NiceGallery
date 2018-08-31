package com.sandra.dupre.repository.gallery

import com.sandra.dupre.business.NetworkException
import com.sandra.dupre.business.NoOtherPageException
import com.sandra.dupre.business.gallery.Picture
import com.sandra.dupre.repository.DataSource
import com.sandra.dupre.repository.pixabay.PicturePixabayEntity
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GalleryRepositoryImplTest {
    @Mock
    lateinit var dataSource: DataSource<List<PicturePixabayEntity>>

    @InjectMocks
    lateinit var repository: GalleryRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun loadNextPictures_WhenNormalCase_ShouldCallDataSource() {
        repository.loadNextPictures()

        then(dataSource).should(only()).loadNextPage()
    }

    @Test
    fun loadPictures_WhenNormalCase_ShouldReturnPictures() {
        given(dataSource.getAll()).willReturn(listOf(PicturePixabayEntity(3, "previewUrl", "fullUrl")))

        val result = repository.loadPictures()

        assertThat(result, equalTo(listOf(Picture(3, "previewUrl", "fullUrl"))))
    }

    @Test(expected = NetworkException::class)
    fun loadNextPictures_WhenDataSourceThrowExpcetion_ShouldPropagateException() {
        given(dataSource.loadNextPage()).willThrow(NetworkException())

        repository.loadNextPictures()
    }

    @Test(expected = NoOtherPageException::class)
    fun loadNextPictures_WhenDataSourceThrowNoOTherPageException_ShouldPropagateException() {
        given(dataSource.loadNextPage()).willThrow(NoOtherPageException())

        repository.loadNextPictures()
    }
}