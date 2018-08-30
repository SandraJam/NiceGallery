package com.sandra.dupre.repository.detail

import com.sandra.dupre.business.PictureNotExistException
import com.sandra.dupre.business.gallery.Picture
import com.sandra.dupre.repository.DataSource
import com.sandra.dupre.repository.pixabay.PicturePixabayEntity
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before

import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailRepositoryImplTest {
    @Mock
    private lateinit var dataSource: DataSource<List<PicturePixabayEntity>>

    @InjectMocks
    private lateinit var detailRepository: DetailRepositoryImpl

    private val picturesPixabayEntity = listOf(
            PicturePixabayEntity(1, "p1", "f1"),
            PicturePixabayEntity(2, "p2", "f2"),
            PicturePixabayEntity(3, "p3", "f3")
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getHDAllPictures_WhenNormalCase_ShouldReturnPictures() {
        given(dataSource.getAll()).willReturn(picturesPixabayEntity)

        val result = detailRepository.getHDAllPictures()

        assertThat(result, equalTo(listOf(
                Picture(1, "p1", "f1"),
                Picture(2, "p2", "f2"),
                Picture(3, "p3", "f3")
        )))
    }

    @Test
    fun getHDPicture_WhenNormalCase_ShouldReturnPicture() {
        given(dataSource.getAll()).willReturn(picturesPixabayEntity)

        val result = detailRepository.getHDPicture(3)

        assertThat(result, equalTo(Picture(3, "p3", "f3")))
    }

    @Test(expected = PictureNotExistException::class)
    fun getHDPicture_WhenNotFind_ShouldThrowPictureNotFoundException() {
        given(dataSource.getAll()).willReturn(picturesPixabayEntity)

        detailRepository.getHDPicture(5)
    }
}