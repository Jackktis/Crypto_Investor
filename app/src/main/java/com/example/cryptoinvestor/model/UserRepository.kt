package com.example.cryptoinvestor.model

class UserRepository {

    // Create a new user with a first and last name
    //TODO: skal password skiftes ud med noget indbygget, så det er krypteret?
    //TODO: Tjek for der ikke eksistere en bruger i forvejen
    fun registerUser(firstName: String, password: String){
        val user = hashMapOf(
            "first" to firstName,
            "password" to password,
            "balance" to 10000,
        )


    }





}