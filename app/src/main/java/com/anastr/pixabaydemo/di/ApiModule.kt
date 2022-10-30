package com.anastr.pixabaydemo.di

import com.anastr.pixabaydemo.data.datasource.PixabayService
import com.anastr.pixabaydemo.utils.Constant
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(
        json: Json,
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(Constant.PIXABAY_BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun providePixabayService(
        retrofit: Retrofit,
    ): PixabayService = retrofit.create(PixabayService::class.java)
}
