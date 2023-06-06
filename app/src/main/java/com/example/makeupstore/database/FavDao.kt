package com.example.makeupstore.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.makeupstore.models.FavProduct

@Dao
interface FavDao {

    @Query("SELECT * FROM favProducts WHERE idUser = :idUser")
    fun getAllFavForUser(idUser: String): LiveData<List<FavProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favProduct: FavProduct): Long

    @Delete
    suspend fun delete(favProduct: FavProduct): Int

    @Query("SELECT * FROM favProducts WHERE name = :productName")
    suspend fun getProduct(productName: String): FavProduct?
}