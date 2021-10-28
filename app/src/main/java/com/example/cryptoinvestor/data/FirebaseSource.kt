package com.example.cryptoinvestor.data

import com.example.cryptoinvestor.model.dto.dto
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

    fun saveUser(email: String, fullname: String, username: String) = firestore.collection("users").document(email).set(
        dto.User(email = email,fullname = fullname, username = username, balance = 10000)
    )

    fun addUserChangeListener(idTokenListener: FirebaseAuth.IdTokenListener){
        firebaseAuth.addIdTokenListener(idTokenListener)
    }
    fun removeUserChangeListener(idTokenListener: FirebaseAuth.IdTokenListener){
        firebaseAuth.removeIdTokenListener(idTokenListener)
    }
}