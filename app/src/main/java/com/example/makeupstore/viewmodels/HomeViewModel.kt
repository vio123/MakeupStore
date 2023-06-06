package com.example.makeupstore.viewmodels

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.makeupstore.R
import com.example.makeupstore.models.CardCategory
import com.example.makeupstore.models.CardProduct
import com.example.makeupstore.network.MakeUpApi
import com.example.makeupstore.network.MakeUpStoreApiService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel(app: Application) : BaseViewModel(app) {
    private val _map: MutableLiveData<Map<String?, MutableList<CardProduct>>> = MutableLiveData()
    val map: LiveData<Map<String?, MutableList<CardProduct>>> = _map

    private val _categories:MutableLiveData<List<CardCategory>> = MutableLiveData()
    val categories:LiveData<List<CardCategory>> = _categories

    private val _products:MutableLiveData<List<CardProduct>> = MutableLiveData()
    val products:LiveData<List<CardProduct>> = _products
    var categoryText:ObservableField<String> = ObservableField()

    fun getAllProducts(){
        viewModelScope.launch {
            try {
                val cardMap: MutableMap<String?, MutableList<CardProduct>> = mutableMapOf()
                val productsList: List<CardProduct> = MakeUpApi.retrofitService.getProducts()
                productsList.forEach { product ->
                    if (product.category != "null" && product.price != "null" && product.category?.isNotEmpty() == true && product.price?.isNotEmpty() == true && product.price != "0.0") {
                        val categoryList = cardMap.getOrDefault(product.category, mutableListOf())
                        categoryList.add(product)
                        cardMap[product.category] = categoryList
                    }
                }

                _map.value = cardMap
            } catch (e: Exception) {
                Log.e("test123", e.message.toString())
            }
        }
    }
    fun getJustForYou() {
        val justForYou:MutableList<CardProduct> = mutableListOf()
        val categories:MutableList<CardCategory> = mutableListOf()
        map.value?.forEach {(key,value)->
            val random = Random.Default
            val randomNumber = random.nextInt(0, value.size)
            categories.add(CardCategory(image = value[randomNumber].image_link.toString(), title = key.toString()))
            justForYou.add(value[randomNumber])
        }
        _products.value = justForYou
        this._categories.value = categories
    }
    fun getProductsByCategory(category:String?){
        _products.value = _map.value?.get(category)
        categoryText.set(category.toString())
    }
}