package com.example.cryptoinvestor.model

import com.example.cryptoinvestor.CryptoInvestApplication
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TransactionRepository (val application : CryptoInvestApplication) {

    fun registerTransaction(amount: Int){
        val currentUserID = "SdfGoPecnyNjpoSnbP9r"

        Firebase.firestore
            .collection("/users/"+currentUserID+"/transaction")
            .add("amount" to amount)


    }

    fun sellTransaction(TransactionID: Int){

    }


}