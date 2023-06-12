package com.example.makeupstore.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordViewModel(app: Application) : BaseViewModel(app) {
    val pass: MutableLiveData<String> = MutableLiveData()

    private val _successOrFailed: MutableLiveData<Boolean> = MutableLiveData()
    val successOrFailed: LiveData<Boolean> = _successOrFailed

    fun changePassword(email: String, oldPassword: String, newPassword: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, oldPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                } else {
                    val exception = task.exception
                    // Handle specific exceptions accordingly
                }
            }

        val user = FirebaseAuth.getInstance().currentUser
        user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                _successOrFailed.value = task.isSuccessful
            }

    }
}