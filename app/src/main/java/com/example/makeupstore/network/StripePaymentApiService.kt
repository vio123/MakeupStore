package com.example.makeupstore.network

import com.example.makeupstore.models.StripeClientSecret
import com.example.makeupstore.models.StripeCustomer
import com.example.makeupstore.models.StripeEpherical
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.Currency

private const val BASE_URL = "https://api.stripe.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface StripePaymentApiService {
    @POST("v1/customers")
    suspend fun getCustomer(
        @Header("Authorization")
        token: String,
    ): StripeCustomer

    @POST("v1/ephemeral_keys")
    suspend fun getEphericalKey(
        @Header("Authorization")
        token: String,
        @Header("Stripe-Version")
        stripeVersion: String = "2022-11-15",
        @Query("customer")
        customerId: String,
    ): StripeEpherical

    @POST("v1/payment_intents")
    suspend fun getClientSecret(
        @Header("Authorization")
        token: String,
        @Query("customer")
        customerId: String,
        @Query("amount")
        amount: Int,
        @Query("currency")
        currency: String = "RON",
        @Query("automatic_payment_methods[enabled]")
        automaticPayment: Boolean = true,
    ): StripeClientSecret
}

object StripeApi {
    val retrofitService: StripePaymentApiService by lazy {
        retrofit.create(StripePaymentApiService::class.java)
    }
}