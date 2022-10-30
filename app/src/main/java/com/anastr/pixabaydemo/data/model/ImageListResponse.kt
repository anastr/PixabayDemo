package com.anastr.pixabaydemo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageListResponse(
    @SerialName("hits") val list: List<ImageInfo>,
)
