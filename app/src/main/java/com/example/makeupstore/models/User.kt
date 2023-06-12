package com.example.makeupstore.models

data class User(
    val nume: String? = "",
    val prenume: String? = "",
    val email: String? = "",
    val contact: String? = "",
    val adresa: String? = "",
    val id: String? = "",
) {
    fun copyWith(
        nume: String? = this.nume,
        prenume: String? = this.prenume,
        email: String? = this.email,
        contact: String? = this.contact,
        adresa: String? = this.adresa,
    ): User {
        return User(nume, prenume, email, contact, adresa, id)
    }

    fun toMap(): Map<String, String?> {
        return mapOf(
            "nume" to nume,
            "prenume" to prenume,
            "email" to email,
            "adresa" to adresa,
            "contact" to contact
        )
    }

}
