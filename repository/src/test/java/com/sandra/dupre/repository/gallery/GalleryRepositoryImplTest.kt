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
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
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
    fun loadPictures_WhenDataSourceReturnPixabayPicture_ShouldReturnPreviewPicture() {
        given(dataSource.get(1)).willReturn(listOf(PicturePixabayEntity(3, "previewUrl", "fullUrl")))
        given(dataSource.get(2)).willReturn(listOf(PicturePixabayEntity(6, "previewUrl2", "fullUrl2")))

        val result = repository.loadPictures(2)

        assertThat(result, equalTo(listOf(
                Picture(3, "previewUrl", "fullUrl"),
                Picture(6, "previewUrl2", "fullUrl2")
        )))
    }

    @Test(expected = NetworkException::class)
    fun loadPictures_WhenDataSourceThrowExpcetion_ShouldPropagateException() {
        given(dataSource.get(1)).willThrow(NetworkException())

        repository.loadPictures(1)
    }

    @Test(expected = NoOtherPageException::class)
    fun loadPictures_WhenDataSourceThrowNoOTherPageException_ShouldPropagateException() {
        given(dataSource.get(1)).willThrow(NoOtherPageException())

        repository.loadPictures(1)
    }
}