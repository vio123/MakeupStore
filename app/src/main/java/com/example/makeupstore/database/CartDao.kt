package com.example.makeupstore.database

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.makeupstore.models.CartProduct

@Dao
interface CartDao {

    @Query("SELECT * FROM cart WHERE idUser = :idUser")
    fun getAllCartForUser(idUser: String): LiveData<List<CartProduct>>


    @Query("SELECT SUM(price) AS total FROM cart")
    suspend fun getTotalPrice(): Double?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartProduct: CartProduct): Long

    @Delete
    suspend fun delete(cartProduct: CartProduct)

    @Query("UPDATE cart SET quantity = :quantity WHERE id = :productId")
    suspend fun updateQuantity(productId: Int, quantity: Int): Int

    @Query("SELECT * FROM cart WHERE name = :productName")
    suspend fun getProduct(productName: String): CartProduct?

    @Query("DELETE FROM cart WHERE idUser = :idUser")
    suspend fun deleteCart(idUser: String): Int

}