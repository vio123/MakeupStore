package com.example.makeupstore.utils

class Validators {
    companion object{
        fun isValidEmail(email:String): Boolean {
            val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
            return emailRegex.matches(email)
        }


        fun isValidPassword(pass:String?):Boolean{
            pass?.let {
                return it.length > 5
            }
            return false
        }
    }
}