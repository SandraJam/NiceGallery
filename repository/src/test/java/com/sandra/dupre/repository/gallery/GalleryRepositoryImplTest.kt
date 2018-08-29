package com.sandra.dupre.repository.gallery

import com.sandra.dupre.business.NetworkException
import com.sandra.dupre.business.gallery.PreviewPicture
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
        given(dataSource.get(1)).willReturn(listOf(PicturePixabayEntity(3, "url")))

        val result = repository.loadPictures()

        assertThat(result, equalTo(listOf(PreviewPicture(3, "url"))))
    }

    @Test(expected = NetworkException::class)
    fun loadPictures_WhenDataSourceThrowExcetion_ShouldPropagateException() {
        given(dataSource.get(1)).willThrow(NetworkException())

        repository.loadPictures()
    }
}