package com.example.cryptoinvestor.model


import com.example.cryptoinvestor.CryptoInvestApplication
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
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

    fun signIn(email: String, password: String): Task<AuthResult>{
        //TODO: Implement custom login or maybe implement google login?
        // auth.signInWithEmailAndPassword()
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun createUser(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email,password)
    }

    fun signOut(){
        auth.signOut()
    }

}