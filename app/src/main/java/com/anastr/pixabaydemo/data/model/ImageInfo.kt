package com.anastr.pixabaydemo.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ImageInfo(
    @SerialName("id") val id: String,
    @SerialName("previewURL") val previewUrl: String,
    @SerialName("webformatURL") val imageUrl: String,
    @SerialName("tags") val tags: String,
    @SerialName("user") val userName: String,
    @SerialName("userImageURL") val userImageUrl: String,
    @SerialName("imageSize") val imageSize: Int,
    @SerialName("type") val type: String,
    @SerialName("views") val views: Int,
    @SerialName("likes") val likes: Int,
    @SerialName("comments") val comments: Int,
    @SerialName("downloads") val downloads: Int,
): Parcelable
