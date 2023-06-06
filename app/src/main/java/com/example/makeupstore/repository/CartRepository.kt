package com.example.makeupstore.repository

import androidx.lifecycle.LiveData
import com.example.makeupstore.database.CartDao
import com.example.makeupstore.models.CardProduct
import com.example.makeupstore.models.CartProduct

class CartRepository(private val cartDao: CartDao) {
    //val allCart: LiveData<List<CartProduct>> = cartDao.getAllCart()

    suspend fun insert(cartProduct: CartProduct): Long {
        return cartDao.insert(cartProduct)
    }

    suspend fun delete(cartProduct: CartProduct) {
        cartDao.delete(cartProduct)
    }

    suspend fun update(id: Int, quantity: Int): Int {
        return cartDao.updateQuantity(id, quantity)
    }

    suspend fun getProduct(productName: String): CartProduct? {
        return cartDao.getProduct(productName)
    }

    fun getAllCartForUser(idUser: String): LiveData<List<CartProduct>> {
        return cartDao.getAllCartForUser(idUser)
    }

    suspend fun deleteCart(idUser: String): Int {
        return cartDao.deleteCart(idUser)
    }

}