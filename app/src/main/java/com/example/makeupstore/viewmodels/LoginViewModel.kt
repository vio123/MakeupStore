package com.example.makeupstore.viewmodels

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.makeupstore.R
import com.example.makeupstore.utils.Validators.Companion.isValidEmail
import com.example.makeupstore.utils.Validators.Companion.isValidPassword
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(app:Application):BaseViewModel(app) {
    val email:MutableLiveData<String> = MutableLiveData()
    val pass:MutableLiveData<String> = MutableLiveData()
    private val _loading:MutableLiveData<Boolean> = MutableLiveData()
    val loading:LiveData<Boolean> = _loading
    private val _error:MutableLiveData<String> = MutableLiveData()
    val error:LiveData<String> = _error
    private val _success:MutableLiveData<String> = MutableLiveData()
    val success:LiveData<String> = _success
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(){
        _loading.value = true
        if(isValidEmail(email.value.toString()) && isValidPassword(pass.value)) {
            mAuth.signInWithEmailAndPassword(
                email.value.toString().trim(),
                pass.value.toString().trim()
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _success.value = "logged"
                    } else {
                        _error.value = task.exception?.message
                        _loading.value = false
                    }
                }
        }else{
            _loading.value = false
        }
    }

    fun clearSuccess(){
        _success.value = ""
    }

    fun clearError(){
        _error.value = ""
    }

    fun clearLoading(){
        _loading.value = false
    }

    fun loginAsGuest(){
        _loading.value = true
        mAuth.signInAnonymously().addOnCompleteListener {task->
            if(task.isSuccessful){
               _success.value = "guest"
            }else{
                _error.value = task.exception?.message
            }
        }
    }

}