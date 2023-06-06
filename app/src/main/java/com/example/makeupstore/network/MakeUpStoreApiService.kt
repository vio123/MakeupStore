package com.example.makeupstore.network

import com.example.makeupstore.models.CardProduct
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://makeup-api.herokuapp.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MakeUpStoreApiService {
    @GET("api/v1/products.json")
    suspend fun getProducts(): List<CardProduct>
}

object MakeUpApi {
    val retrofitService: MakeUpStoreApiService by lazy {
        retrofit.create(MakeUpStoreApiService::class.java)
    }
}