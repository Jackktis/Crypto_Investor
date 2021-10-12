package com.example.cryptoinvestor.model


import com.example.cryptoinvestor.CryptoInvestApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class AuthRepository(val application: CryptoInvestApplication) {
    private var auth = application.auth



    fun addUserChangeListener(idTokenListener: FirebaseAuth.IdTokenListener){
        auth.addIdTokenListener(idTokenListener)
    }
    fun removeUserChangeListener(idTokenListener: FirebaseAuth.IdTokenListener){
        auth.removeIdTokenListener(idTokenListener)
    }

    fun signInWithEmailAndPassword(){
        //TODO: Implement custom login or maybe implement google login?
        // auth.signInWithEmailAndPassword()
    }

    fun signOut(){
        auth.signOut()
    }

}