package com.example.makeupstore.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.makeupstore.database.AppDatabase
import com.example.makeupstore.models.CartProduct
import com.example.makeupstore.network.StripeApi
import com.example.makeupstore.repository.CartRepository
import com.example.makeupstore.utils.StripeUtils
import com.example.makeupstore.utils.UserUtils
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel(app: Application) : BaseViewModel(app) {
    private val repository: CartRepository
    private val database = Firebase.database

    init {
        val dao = AppDatabase.getDatabase(app).cartDao()
        repository = CartRepository(dao)
    }

    val allCart: LiveData<List<CartProduct>> = repository.getAllCartForUser(UserUtils.getUserId())

    fun delete(cartProduct: CartProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(cartProduct)
        }
    }

    suspend fun getTotalPrice():Double?{
        return withContext(viewModelScope.coroutineContext){
            repository.getTotalPrice()
        }
    }

    suspend fun checkout(): Int {
        allCart.value?.forEach { cartProduct ->
            val myRef = database.getReference("Checkouts").child(UserUtils.getUserId()).push()
            myRef.setValue(cartProduct)
        }
        return withContext(viewModelScope.coroutineContext) {
            repository.deleteCart(UserUtils.getUserId())
        }
    }

    suspend fun getCustomer(): String {
        return try {
            withContext(viewModelScope.coroutineContext) {
                StripeApi.retrofitService.getCustomer(token = "Bearer ${StripeUtils.PRIVATE_KEY}").customerId.toString()
            }
        } catch (e: Exception) {
            e.toString()
        }
    }

    suspend fun getClientSecret(customerId: String, amount: Int): String {
        return try {
            withContext(viewModelScope.coroutineContext) {
                StripeApi.retrofitService.getClientSecret(
                    token = "Bearer ${StripeUtils.PRIVATE_KEY}",
                    customerId,
                    amount = amount
                ).clientSecret.toString()
            }
        } catch (e: Exception) {
            e.toString()
        }
    }


    suspend fun updateQuantity(quantity: Int, prodId: Int): Int {
        return withContext(viewModelScope.coroutineContext) {
            repository.update(quantity = quantity, id = prodId)
        }
    }
}