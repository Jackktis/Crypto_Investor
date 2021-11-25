package com.example.cryptoinvestor.model

import com.example.cryptoinvestor.CryptoInvestApplication
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class TransactionRepository () {
    val currentUserID = "D0gjXmihfLebZdZlzpQl"

    fun buyTransaction(coinName: String, totalPrice : Double, quantity : Double){
        // Data that need to be sent to "users/-someUser-/
        val tData = mapOf(
            "Currency Name" to coinName,
            "Price" to totalPrice,
            "Quantity" to quantity,
            "Time" to Timestamp.now()
        )

        Firebase.firestore
            .collection("/users/"+currentUserID+"/transaction")
            .add(tData)


    }

    fun sellTransaction(TransactionID: Int){

    }


}