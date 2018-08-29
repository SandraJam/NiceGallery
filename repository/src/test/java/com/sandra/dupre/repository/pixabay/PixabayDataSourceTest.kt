package com.sandra.dupre.repository.pixabay

import com.sandra.dupre.business.NetworkException
import com.sandra.dupre.business.NoOtherPageException
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
    private lateinit var retrofit: PixabayServices
    @Mock
    private lateinit var call: Call<PixabayEntity>

    @InjectMocks
    private lateinit var dataSource: PixabayDataSource

    private val pictures = listOf(PicturePixabayEntity(3, "url"))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        given(retrofit.listPictures(1)).willReturn(call)
    }

    @Test
    fun get_WhenNoPicturesInMap_ShouldCallRetrofitAndReturnPictures() {
        given(call.execute()).willReturn(Response.success(
                PixabayEntity(400, pictures)
        ))

        val result = dataSource.get(1)

        assertThat(result, equalTo(pictures))
        assertThat(dataSource.picturesMap[1], equalTo(PixabayEntity(400, pictures)))
    }

    @Test
    fun get_WhenPicturesInMap_ShouldCallReturnPictures() {
        dataSource.picturesMap[1] = PixabayEntity(400, pictures)

        val result = dataSource.get(1)

        assertThat(result, equalTo(pictures))
    }

    @Test(expected = NoOtherPageException::class)
    fun get_WhenNoOtherPage_ShouldThrowException() {
        dataSource.picturesMap[1] = PixabayEntity(10, pictures)

        val result = dataSource.get(2)
    }

    @Test(expected = NetworkException::class)
    fun get_WhenCallException_ShouldThrowNetworkException() {
        given(call.execute()).willThrow(IOException())

        dataSource.get(1)
    }

    @Test(expected = NetworkException::class)
    fun get_WhenBodyIsNull_ShouldThrowNetworkException() {
        given(call.execute()).willReturn(Response.success(null))

        dataSource.get(1)
    }
}