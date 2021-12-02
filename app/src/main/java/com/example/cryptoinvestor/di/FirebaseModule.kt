package com.example.cryptoinvestor.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
    Firebase Hilt Module class that is installed in a Hilt Component
    The module component type is SingletonComponent, which means anything in the application can use it.
    The scope annotation of the functions are set to Singleton, to scope the binding to the SingletonComponent
 */
@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFireBaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore() = FirebaseFirestore.getInstance()
}