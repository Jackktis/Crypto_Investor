package com.example.cryptoinvestor.model


import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cryptoinvestor.CryptoInvestApplication
import com.example.cryptoinvestor.data.FirebaseSource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
import kotlin.concurrent.thread

class AuthRepository @Inject constructor(private val firebaseSource: FirebaseSource) {

    fun signOut() = firebaseSource.signOut()

    fun signIn(email: String, password: String) = firebaseSource.signIn(email, password)


    fun signUp(email: String, password: String, fullName: String, userName: String) =
        firebaseSource.createUser(email, password, fullName, userName)

    fun saveUser(email: String, fullName: String, userName: String, balance: Int?, userId: String) =
        firebaseSource.saveUser(email, fullName, userName, balance, userId)

    fun getUserId() = firebaseSource.getCurrentUserID()

    fun fetchUser() = firebaseSource.fetchUser()

    fun getUserBalance(myCallback: (String) -> Unit){
        val userBalance = Firebase.firestore.collection("users").document("${getUserId()}")

        // Get the document, forcing the SDK to use the offline cache
        userBalance.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    println(" Balance value from AuthRep:  ${document.data?.get("balance").toString()}")
                    var balance = document.data?.get("balance").toString()

                    //callback on the result of the network work
                    myCallback.invoke(balance)
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }


}