package com.example.cryptoinvestor.model


import com.example.cryptoinvestor.data.FirebaseSource
import javax.inject.Inject

/*
    Repository class for getting
 */
class AuthRepository @Inject constructor(private val firebaseSource: FirebaseSource) {

    fun signOut() = firebaseSource.signOut()

    fun signIn(email: String, password: String) = firebaseSource.signIn(email, password)


    fun signUp(email: String, password: String) =
        firebaseSource.createUser(email, password)

    fun saveUser(email: String, fullName: String, userName: String, balance: Double?, userId: String) =
        firebaseSource.saveUser(email, fullName, userName, balance, userId)

    fun getUserId() = firebaseSource.getCurrentUserID()

    fun fetchUser() = firebaseSource.fetchUser()

}