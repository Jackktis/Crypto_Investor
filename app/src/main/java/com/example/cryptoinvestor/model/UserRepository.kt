package com.example.cryptoinvestor.model

import android.content.ContentValues
import android.util.Log
import com.example.cryptoinvestor.data.FirebaseSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class UserRepository @Inject constructor(private val firebaseSource: FirebaseSource) {

    private fun getUserId() = firebaseSource.getCurrentUserID()

    fun getUserBalance(myCallback: (String) -> Unit) {
        //Assigning the path to the document
        val userBalance = Firebase.firestore.collection("users").document("${getUserId()}")

        // Get the document, forcing the SDK to use the offline cache
        userBalance.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    println(
                        " Balance value from AuthRep:  ${
                            document.data?.get("balance").toString()
                        }"
                    )
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

    fun getUserQuantity(coinName: String, myCallback: (Double) -> Unit) {
        //Setting the path of the coinName to a value
        val cryptoName =
            Firebase.firestore.collection("/users/${getUserId()}/portfolio").document(coinName)
        var cryptoQuantity = 0.0
        //Network call to get the document of the path
        cryptoName.get()
            .addOnSuccessListener { document ->
                //If there is a document of the coinName containing anything then get the value of the quantity
                if (document.exists()) {
                    cryptoQuantity = document.data?.getValue("Quantity").toString().toDouble()
                }
                //Return the result of the network work
                myCallback.invoke(cryptoQuantity)
            }
    }

}