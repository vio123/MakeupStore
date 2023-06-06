package com.example.makeupstore.utils

import com.example.makeupstore.models.CartProduct
import com.example.makeupstore.models.FavProduct


interface OnItemClickListener {
    fun onItemClick(position: Int)

    fun onItemDeleteCart(cartProduct: CartProduct)

    fun onUpdateQuantity(quantity:Int,prodId:Int)

    fun onItemDeleteFav(favProduct: FavProduct)
}