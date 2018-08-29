package com.sandra.dupre.repository.pixabay

import com.sandra.dupre.business.NetworkException
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.junit.Before

import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class PixabayDataSourceTest {
    @Mock
    lateinit var retrofit: PixabayServices
    @Mock
    lateinit var call: Call<PixabayEntity>

    @InjectMocks
    lateinit var dataSource: PixabayDataSource

    val pictures = listOf(PicturePixabayEntity(3, "url"))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        given(retrofit.listPictures()).willReturn(call)
    }

    @Test
    fun get_WhenNoPicturesInMap_ShouldCallRetrofitAndReturnPictures() {
        given(call.execute()).willReturn(Response.success(
                PixabayEntity(400, pictures)
        ))

        val result = dataSource.get()

        assertThat(result, equalTo(pictures))
        assertThat(dataSource.picturesMap[1], equalTo(pictures))
    }

    @Test
    fun get_WhenPicturesInMap_ShouldCallReturnPictures() {
        dataSource.picturesMap[1] = pictures

        val result = dataSource.get()

        assertThat(result, equalTo(pictures))
    }

    @Test(expected = NetworkException::class)
    fun get_WhenCallException_ShouldThrowNetworkException() {
        given(call.execute()).willThrow(IOException())

        dataSource.get()
    }

    @Test(expected = NetworkException::class)
    fun get_WhenBodyIsNull_ShouldThrowNetworkException() {
        given(call.execute()).willReturn(Response.success(null))

        dataSource.get()
    }
}