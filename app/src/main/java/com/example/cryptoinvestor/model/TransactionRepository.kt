package com.example.cryptoinvestor.model

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.awaitAll
import java.util.*
import javax.inject.Inject


class TransactionRepository @Inject constructor (private val auth : AuthRepository) {
    //val currentUserID = "D0gjXmihfLebZdZlzpQl"
    val currentUserID = auth.getUserId()

    fun registerTransaction(coinName: String, totalPrice : Double, quantity : Double, tag : String){
        // Data that need to be sent to "users/-someUserID-/transaction"
        var tData = mapOf(
            "Currency Name" to coinName,
            "Price" to totalPrice,
            "Quantity" to quantity,
            "Action" to tag,
            "Time" to Timestamp.now()
        )

        Firebase.firestore
            .collection("/users/"+currentUserID+"/transaction")
            .add(tData)
    }

    fun buyTransaction(coinName: String, totalPrice : Double, quantity : Double, balance : Double){
        var finalQuantity = quantity
        var finalPrice = totalPrice

        // Check if user already own some quantity of a coin
        val docRef = Firebase.firestore
            .collection("/users/"+currentUserID+"/portfolio")
            .document(coinName)

        docRef.get()
            .addOnSuccessListener { document ->

                // If we already own some quantity of the crypto currency
                if (document.exists()) {
                    finalQuantity += document.get("Quantity").toString().toDouble()
                    finalPrice += document.get("Price").toString().toDouble()
                }

                // Data that need to be sent to "users/-someUserID-/transaction"
                val tData = mapOf(
                    "Currency Name" to coinName,
                    "Price" to finalPrice,
                    "Quantity" to finalQuantity
                )

                Firebase.firestore
                        .collection("/users/"+currentUserID+"/portfolio").document(coinName)
                    .set(tData)
            }
        val newBalance = balance - totalPrice

        if (currentUserID != null) {
            Firebase.firestore
                .collection("/users/").document(currentUserID).update(mapOf("balance" to newBalance))
        }
    }

    fun sellTransaction(coinName: String, totalPrice : Double, quantity : Double){
        val tData = mapOf(
            "Currency Name" to coinName,
            "Price" to totalPrice,
            "Quantity" to quantity
        )

        // Need a getter so of the current portfolio and then subtract from it
        Firebase.firestore
            .collection("/users/"+currentUserID+"/portfolio").document(coinName)
            .set(tData)
    }


}