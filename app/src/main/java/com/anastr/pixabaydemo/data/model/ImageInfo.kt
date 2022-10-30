package com.anastr.pixabaydemo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageInfo(
    @SerialName("id") val id: String,
    @SerialName("previewURL") val previewUrl: String,
    @SerialName("webformatURL") val imageUrl: String,
    @SerialName("tags") val tags: String,
    @SerialName("user") val userName: String,
    @SerialName("userImageURL") val userImageUrl: String,
    @SerialName("imageSize") val imageSize: String,
    @SerialName("type") val type: String,
    @SerialName("views") val views: String,
    @SerialName("likes") val likes: String,
    @SerialName("comments") val comments: String,
    @SerialName("downloads") val downloads: String,
)
