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

    private val pictures = listOf(PicturePixabayEntity(3, "previewUrl", "largeUrl"))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        given(retrofit.listPictures("2952852-222a829dd5bea68bfc7fc69ec", 1, 32, null)).willReturn(call)
        given(retrofit.listPictures("2952852-222a829dd5bea68bfc7fc69ec", 2, 32, null)).willReturn(call)
    }

    @Test
    fun getAll_WhenNormalCase_ShouldReturnPicturesList() {
        dataSource.pictures.addAll(pictures)

        val result = dataSource.getAll()

        assertThat(result, equalTo(pictures))
    }

    @Test
    fun loadNextPage_WhenOk_ShouldPutPicturesInList() {
        given(call.execute()).willReturn(Response.success(PixabayEntity(400, pictures)))

        dataSource.loadNextPage(null)

        assertThat(dataSource.pictures, equalTo(pictures))
    }

    @Test(expected = NoOtherPageException::class)
    fun loadNextPage_WhenNoOtherPage_ShouldThrowException() {
        given(call.execute()).willReturn(Response.success(PixabayEntity(10, pictures)))

        dataSource.loadNextPage(null)
        dataSource.loadNextPage(null)
    }

    @Test(expected = NetworkException::class)
    fun loadNextPage_WhenCallException_ShouldThrowNetworkException() {
        given(call.execute()).willThrow(IOException())

        dataSource.loadNextPage(null)
    }

    @Test(expected = NetworkException::class)
    fun loadNextPage_WhenBodyIsNull_ShouldThrowNetworkException() {
        given(call.execute()).willReturn(Response.success(null))

        dataSource.loadNextPage(null)
    }
}