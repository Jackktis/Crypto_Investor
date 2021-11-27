package com.example.cryptoinvestor.model

import android.content.ContentValues
import android.util.Log
import com.example.cryptoinvestor.CryptoInvestApplication
import com.example.cryptoinvestor.data.FirebaseSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class UserRepository @Inject constructor(private val firebaseSource: FirebaseSource) {

    fun getUserId() = firebaseSource.getCurrentUserID()

    fun getUserBalance(myCallback: (String) -> Unit) {
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
                    Log.d(ContentValues.TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }
    }
}