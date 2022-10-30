package com.anastr.pixabaydemo.data.datasource

import com.anastr.pixabaydemo.data.model.ImageListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {
    @GET("api")
    suspend fun listRepos(@Query("key") key: String): ImageListResponse
}