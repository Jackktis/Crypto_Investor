package com.example.cryptoinvestor.model

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.example.cryptoinvestor.model.api.dto.TransactionDto
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.awaitAll
import java.lang.String.format
import java.sql.Types.TIMESTAMP
import java.util.*
import javax.inject.Inject


class TransactionRepository @Inject constructor (private val auth : AuthRepository) {
    //val currentUserID = "D0gjXmihfLebZdZlzpQl"
    private val currentUserID = auth.getUserId()

    fun registerTransaction(coinName: String, symbol: String, totalPrice : Double, quantity : Double, tag : String){
        // Data that need to be sent to "users/-someUserID-/transaction"
        var tData = mapOf(
            "Currency Name" to coinName,
            "Symbol" to symbol,
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

    fun getTransaction(myCallback: (MutableList<TransactionDto>) -> Unit) {
        var TransactionList = mutableListOf<TransactionDto>()

        val usersTransaction = Firebase.firestore.collection("users")
            .document("$currentUserID")
            .collection("transaction")

        // Get the document, forcing the SDK to use the offline cache
        usersTransaction.get()
            .addOnSuccessListener { result  ->
                for (document in result) {

                    val action = document.data?.getValue("Action").toString()
                    val currencyName = document.data?.getValue("Currency Name").toString()
                    val symbol = document.data?.getValue("Symbol").toString()
                    val price = document.data?.getValue("Price").toString().toFloat()
                    val quantity = document.data?.getValue("Quantity").toString().toFloat()
                    val time = format(document.data?.getValue("Time").toString())

                    TransactionList.add(TransactionDto(action,currencyName,symbol,price,quantity,time ))

                    myCallback(TransactionList)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }
    }


}