package com.example.makeupstore.models

import com.squareup.moshi.Json

data class CardProduct(
    @Json(name = "id")
    val id: Int,
    @Json(name = "brand")
    val brand: String?,
    @Json(name = "name")
    val name: String ?= "",
    @Json(name = "price")
    val price: String? = "",
    @Json(name = "price_sign")
    val priceSign: String? = "",
    @Json(name = "image_link")
    val image_link: String? = "",
    @Json(name = "category")
    val category: String? = "" ,
    @Json(name = "description")
    val description:String? = ""
)
