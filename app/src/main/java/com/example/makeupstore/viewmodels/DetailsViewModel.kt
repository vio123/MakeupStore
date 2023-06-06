package com.example.makeupstore.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.makeupstore.database.AppDatabase
import com.example.makeupstore.models.CartProduct
import com.example.makeupstore.models.FavProduct
import com.example.makeupstore.repository.CartRepository
import com.example.makeupstore.repository.FavRepository
import kotlinx.coroutines.withContext

class DetailsViewModel(app: Application) : BaseViewModel(app) {
    private val repositoryCart: CartRepository
    private val repositoryFav: FavRepository

    init {
        val daoCart = AppDatabase.getDatabase(app).cartDao()
        val daoFav = AppDatabase.getDatabase(app).favDao()
        repositoryCart = CartRepository(daoCart)
        repositoryFav = FavRepository(daoFav)
    }

    suspend fun addToCart(cartProduct: CartProduct): Long {
        val prod = repositoryCart.getProduct(productName = cartProduct.name.toString())
        return if (prod != null) {
            withContext(viewModelScope.coroutineContext) {
                repositoryCart.update(prod.id, prod.quantity + 1).toLong()
            }
        } else {
            withContext(viewModelScope.coroutineContext) {
                repositoryCart.insert(cartProduct)
            }
        }
    }

    suspend fun addToFav(favProduct: FavProduct): Long {
        val prod = repositoryFav.getProduct(productName = favProduct.name.toString())
        return if (prod != null) {
            repositoryFav.delete(prod).toLong()
        } else {
            withContext(viewModelScope.coroutineContext) {
                repositoryFav.insert(favProduct)
            }
        }
    }

    suspend fun isFav(productName: String): Boolean {
        val prod = repositoryFav.getProduct(productName)
        if(prod!=null){
            return true
        }
        return false
    }
}