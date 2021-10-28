package com.example.cryptoinvestor.di

import com.example.cryptoinvestor.CryptoInvestApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn(CryptoInvestApplication::class)
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFireBaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun  provideFirestore() = FirebaseFirestore.getInstance()
}