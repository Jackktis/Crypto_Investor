package com.example.cryptoinvestor.model


import com.example.cryptoinvestor.CryptoInvestApplication
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference

class AuthRepository(val application: CryptoInvestApplication) {
    private var auth = application.auth
    private var firestore = application.firestore



    fun addUserChangeListener(idTokenListener: FirebaseAuth.IdTokenListener){
        auth.addIdTokenListener(idTokenListener)
    }
    fun removeUserChangeListener(idTokenListener: FirebaseAuth.IdTokenListener){
        auth.removeIdTokenListener(idTokenListener)
    }

    fun signIn(email: String, password: String): Task<AuthResult>{
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun getCurrentUserID(): String? {
        return auth.currentUser?.uid
    }

    //Perhaps we dont need the method here, since its not authentication but Firestore
    fun userDocCollection(userId : String): DocumentReference {
        return firestore.collection("users").document(userId)
    }

    fun createUser(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email,password)
    }

    fun signOut(){
        auth.signOut()
    }

}