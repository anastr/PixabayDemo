package com.anastr.pixabaydemo.data.repository

import com.anastr.pixabaydemo.data.datasource.PixabayService
import com.anastr.pixabaydemo.data.model.ImageInfo
import com.anastr.pixabaydemo.state.CallState
import com.anastr.pixabaydemo.utils.Constant
import javax.inject.Inject

class ImageListRepository @Inject constructor(
    private val service: PixabayService,
) {

    suspend fun getImages(): CallState<List<ImageInfo>> {
        return try {
            val response = service.listRepos(Constant.PIXABAY_KEY)
            CallState.Success(response.list)
        } catch (e: Exception) {
            CallState.Error(e.message ?: "Connection error!")
        }
    }
}
