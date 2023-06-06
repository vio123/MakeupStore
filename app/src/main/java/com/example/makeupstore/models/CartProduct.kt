package com.example.makeupstore.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartProduct(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "idUser")
    val idUser:String,
    @ColumnInfo(name = "name")
    val name: String ?= "",
    @ColumnInfo(name = "price")
    val price: String = "",
    @ColumnInfo(name = "image_link")
    val image_link: String? = "",
    @ColumnInfo(name = "description")
    val description:String? = "",
    @ColumnInfo(name = "quantity")
    var quantity:Int
)
