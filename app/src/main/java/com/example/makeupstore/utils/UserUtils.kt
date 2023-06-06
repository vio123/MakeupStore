package com.example.makeupstore.utils

import com.google.firebase.auth.FirebaseAuth

object UserUtils {
    private val auth = FirebaseAuth.getInstance()
    fun isUserGuest(): Boolean {
        return auth.currentUser?.isAnonymous == true
    }

    fun getUserId(): String {
        return auth.currentUser?.uid.toString()
    }

    fun isAlreadyLogged(): Boolean {
        if (!isUserGuest() && auth.currentUser != null) {
            return true
        }
        return false
    }

    fun logOut(){
        auth.signOut()
    }
}