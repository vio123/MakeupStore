package com.example.makeupstore.models

import com.squareup.moshi.Json

data class StripeCustomer(
    @Json(name = "id")
    val customerId: String? = "",
)
