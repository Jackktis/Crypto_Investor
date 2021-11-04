package com.example.cryptoinvestor

import android.app.Application
import com.example.cryptoinvestor.model.AuthRepository
import com.example.cryptoinvestor.model.TransactionRepository
import com.example.cryptoinvestor.model.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.HiltAndroidApp

/* ---------SERVICE LOCATOR CLASS--------- */

@HiltAndroidApp
class CryptoInvestApplication : Application() {
    lateinit var auth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    lateinit var storage: FirebaseStorage

    //lateinit var authRepository : AuthRepository
    lateinit var userRepository: UserRepository
    lateinit var transactionRepository: TransactionRepository

    override fun onCreate() {
        super.onCreate()

        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage

        //authRepository = AuthRepository(this)
        userRepository = UserRepository(this)
        transactionRepository = TransactionRepository(this)

    }

}