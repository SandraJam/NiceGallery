package com.sandra.dupre.repository.pixabay

import retrofit2.Call
import retrofit2.http.GET

interface PixabayServices {

    @GET("?key=2952852-222a829dd5bea68bfc7fc69ec&image_type=photo&per_page=32&page=1")
    fun listPictures(): Call<PixabayEntity>
}
