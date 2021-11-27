package com.example.cryptoinvestor.data

import android.content.ContentValues.TAG
import android.util.Log
import com.example.cryptoinvestor.model.api.dto.User
//import com.example.cryptoinvestor.model.dto.dto
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseSource @Inject constructor(private val firebaseAuth: FirebaseAuth, private val firestore: FirebaseFirestore) {

    fun createUser(email: String, password: String, fullName: String, userName: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email,password)
    }

    fun signIn(email: String, password: String): Task<AuthResult>{
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    fun signOut(){
        firebaseAuth.signOut()
    }

    fun getCurrentUserID(): String? {
        return firebaseAuth.currentUser?.uid
    }

    fun fetchUser() = firestore.collection("users").get()

    fun saveUser(email: String, fullName: String, userName: String, balance: Double?, userId: String) = firestore.collection("users").document(userId)
        .set(User(email = email, fullName = fullName, userName = userName, balance = balance, userId = userId))
        .addOnSuccessListener { Log.d(TAG,"Success!!!!!!") }
        .addOnFailureListener{e -> Log.w(TAG, "ERORRR!!!!!", e)}

    fun addUserChangeListener(idTokenListener: FirebaseAuth.IdTokenListener){
        firebaseAuth.addIdTokenListener(idTokenListener)
    }
    fun removeUserChangeListener(idTokenListener: FirebaseAuth.IdTokenListener){
        firebaseAuth.removeIdTokenListener(idTokenListener)
    }
}