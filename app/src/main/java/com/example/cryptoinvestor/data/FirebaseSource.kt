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

    /*
        Source class for retrieving the API methods for both FirebaseAuth and FirebaseFirestore
     */


    //Method for creating a new user with email and password through FirebaseAuth.
    fun createUser(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email,password)
    }

    //Method for signing in an user with email and password
    fun signIn(email: String, password: String): Task<AuthResult>{
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    //Method for signing out
    fun signOut(){
        firebaseAuth.signOut()
    }

    //Method for getting the currently signed in user
    fun getCurrentUserID(): String? {
        return firebaseAuth.currentUser?.uid
    }

    //Method for getting the collections of users
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