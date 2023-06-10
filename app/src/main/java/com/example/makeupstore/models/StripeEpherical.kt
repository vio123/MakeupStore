package com.example.makeupstore.models

import com.squareup.moshi.Json

data class StripeEpherical(
    @Json(name = "id")
    val id: String? = "",
)
