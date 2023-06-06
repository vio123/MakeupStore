package com.example.makeupstore.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.makeupstore.utils.Validators.Companion.isValidEmail
import com.example.makeupstore.utils.Validators.Companion.isValidPassword
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel(app:Application): BaseViewModel(app) {
    val email: MutableLiveData<String> = MutableLiveData()
    val pass: MutableLiveData<String> = MutableLiveData()
    private val _loading:MutableLiveData<Boolean> = MutableLiveData()
    val loading:LiveData<Boolean> = _loading
    private val _error:MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error
    private val _success:MutableLiveData<String> = MutableLiveData()
    val success: LiveData<String> = _success
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    fun register(){
        _error.value = ""
        _success.value = ""
        _loading.value = true
        if(isValidEmail(email.value.toString()) && isValidPassword(pass.value)) {
            mAuth.createUserWithEmailAndPassword(
                email.value.toString().trim(),
                pass.value.toString().trim()
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _success.value = "registered"
                    } else {
                        _error.value = "error"
                    }
                    _loading.value = false
                }
        }else{
            _loading.value = false
        }
    }
}