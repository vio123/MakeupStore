package com.example.makeupstore.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.makeupstore.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SettingsViewModel(app:Application):BaseViewModel(app) {
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
    fun getEditTextVisibility(nume: String?): Int {
        return if (nume != null && nume.isNotEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
    fun updatetUserData(user: User?){
        val userReference = database.getReference("Users").child(user?.id.toString())
        user?.toMap()?.let {
            userReference.updateChildren(it)
                .addOnSuccessListener {

                }
                .addOnFailureListener { error ->

                }
        }

    }
}