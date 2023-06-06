package com.example.makeupstore.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.makeupstore.models.ProfileProduct
import com.example.makeupstore.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ProfileViewModel(app: Application) : BaseViewModel(app) {
    private val database = Firebase.database

    fun getUserData(userId: String): LiveData<User?> {
        val userReference = database.getReference("Users").child(userId)
        val userData = MutableLiveData<User?>()
        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                userData.value = user
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error appropriately
            }
        })
        return userData
    }

    fun getUserCheckouts(userId: String): LiveData<List<ProfileProduct?>> {
        val checkoutReference = database.getReference("Checkouts").child(userId)
        val checkoutsData = MutableLiveData<List<ProfileProduct?>>()
        checkoutReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val checkoutsList = mutableListOf<ProfileProduct?>()
                for (sp in snapshot.children) {
                    val data = sp.getValue(ProfileProduct::class.java)
                    data?.let { checkoutsList.add(it) }
                }
                checkoutsData.value = checkoutsList
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return checkoutsData
    }
}