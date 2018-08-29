package com.sandra.dupre.repository.pixabay

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayServices {

    @GET("?key=2952852-222a829dd5bea68bfc7fc69ec&image_type=photo")
    fun listPictures(@Query("page") page: Int, @Query("per_page") perPage: Int): Call<PixabayEntity>
}
