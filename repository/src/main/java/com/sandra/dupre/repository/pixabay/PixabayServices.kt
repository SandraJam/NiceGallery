package com.sandra.dupre.repository.pixabay

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayServices {

    @GET("?image_type=photo")
    fun listPictures(
            @Query("key") key: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int): Call<PixabayEntity>
}
