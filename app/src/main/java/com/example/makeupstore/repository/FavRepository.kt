package com.example.makeupstore.repository

import androidx.lifecycle.LiveData
import com.example.makeupstore.database.FavDao
import com.example.makeupstore.models.FavProduct

class FavRepository(private val favDao: FavDao) {

    suspend fun insert(favProduct: FavProduct): Long {
        return favDao.insert(favProduct)
    }

    suspend fun delete(favProduct: FavProduct): Int {
        return favDao.delete(favProduct)
    }

    fun getAllFavForUser(idUser: String): LiveData<List<FavProduct>> {
        return favDao.getAllFavForUser(idUser)
    }

    suspend fun getProduct(productName: String): FavProduct? {
        return favDao.getProduct(productName)
    }
}