package com.example.makeupstore.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.makeupstore.database.AppDatabase
import com.example.makeupstore.models.FavProduct
import com.example.makeupstore.repository.FavRepository
import com.example.makeupstore.utils.UserUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(app: Application) : BaseViewModel(app) {
    private val repository: FavRepository

    init {
        val dao = AppDatabase.getDatabase(app).favDao()
        repository = FavRepository(dao)
    }

    val allFav: LiveData<List<FavProduct>> = repository.getAllFavForUser(UserUtils.getUserId())

    fun delete(favProduct: FavProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(favProduct)
        }
    }
}