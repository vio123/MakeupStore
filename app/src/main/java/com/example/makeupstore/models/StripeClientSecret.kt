package com.example.makeupstore.models

import com.squareup.moshi.Json

data class StripeClientSecret(
    @Json(name = "client_secret")
    val clientSecret: String? = "",
)
