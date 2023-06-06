package com.example.makeupstore.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class ProfileProduct(
    val id: Int = 0,
    val idUser: String? = "",
    val name: String? = "",
    val price: String = "",
    val image_link: String? = "",
    val description: String? = "",
    var quantity: Int = 0,
)
