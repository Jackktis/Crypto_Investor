package com.example.cryptoinvestor.model

import com.example.cryptoinvestor.CryptoInvestApplication

class UserRepository (val application: CryptoInvestApplication) {

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