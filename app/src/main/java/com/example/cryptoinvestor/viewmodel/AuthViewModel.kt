package com.example.cryptoinvestor.viewmodel

import android.content.ContentValues.TAG
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoinvestor.model.AuthRepository
import com.example.cryptoinvestor.model.api.dto.User
//import com.example.cryptoinvestor.model.dto.dto
import com.example.cryptoinvestor.utils.NetworkCheck
import com.example.cryptoinvestor.vo.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firebaseAuth: FirebaseAuth,
    private val networkCheck: NetworkCheck
) : ViewModel() {

    private val userLiveData = MutableLiveData<Resource<User>>()
    private val _saveUserLiveData = MutableLiveData<Resource<User>>()
    val saveUserData = _saveUserLiveData
    private val pwMaxLength = 8

    fun signUp(
        email: String,
        password: String,
        fullName: String,
        userName: String
    ): LiveData<Resource<User>> {
        when {
            TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(fullName) && TextUtils.isEmpty(
                userName
            ) -> {
                userLiveData.postValue(Resource.error("field cannot be empty", null))
            }
            password.length < pwMaxLength -> {
                userLiveData.postValue(
                    Resource.error(
                        "Password cannot be less than $pwMaxLength",
                        null
                    )
                )
            }
            networkCheck.isConnected() -> {
                userLiveData.postValue(Resource.loading(null))
                firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener() {
                    if (it.result?.signInMethods?.size == 0) {
                        authRepository.signUp(email, password, fullName, userName)
                            .addOnCompleteListener() { task ->
                                if (task.isSuccessful) {
                                    firebaseAuth.currentUser?.sendEmailVerification()
                                    userLiveData.postValue(
                                        Resource.success(
                                            User(
                                                email = email, fullName = fullName,
                                                userName = userName, balance = 0.0
                                            )
                                        )
                                    )
                                } else {
                                    userLiveData.postValue(
                                        Resource.error(
                                            it.exception?.message.toString(),
                                            null
                                        )
                                    )
                                }
                            }
                    } else {
                        userLiveData.postValue(Resource.error("The email does already exist", null))
                    }
                }

            }
            else -> {
                userLiveData.postValue(Resource.error("No internet connection", null))
            }
        }
        return userLiveData
    }

    fun saveUser(email: String, fullName: String, userName: String, balance: Double?, userId: String) {
        authRepository.saveUser(email, fullName, userName, balance, userId).addOnCompleteListener() {
            if (it.isSuccessful) {
                _saveUserLiveData.postValue(
                    Resource.success(
                        User(
                            email,
                            fullName,
                            userName,
                            balance,
                            userId
                        )
                    )
                )
            }
        }
    }

    fun signOut() = viewModelScope.launch {
        authRepository.signOut()
    }

    fun signIn(email: String, password: String): LiveData<Resource<User>> {
        when {
            TextUtils.isEmpty(email) && TextUtils.isEmpty(password) -> {
                userLiveData.postValue(Resource.error("Enter email and password", null))
            }
            networkCheck.isConnected() -> {
                userLiveData.postValue(Resource.loading(null))
                firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener {
                    if (it.result?.signInMethods?.size == 0) {
                        userLiveData.postValue(Resource.error("Email does not exist", null))
                    } else {
                        authRepository.signIn(email, password).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                firebaseAuth.currentUser?.isEmailVerified?.let { verified ->
                                    if (verified) {
                                        authRepository.fetchUser()
                                            .addOnCompleteListener { userTask ->
                                                if (userTask.isSuccessful) {
                                                    userTask.result?.documents?.forEach {
                                                        if (it.data!!["email"] == email) {
                                                            val name = it.data?.getValue("fullName")
                                                            val userName = it.data?.getValue("userName")
                                                            val balance = it.data?.getValue("balance")
                                                            userLiveData.postValue(
                                                                Resource.success(
                                                                    User(
                                                                        firebaseAuth.currentUser?.email!!,
                                                                        name?.toString()!!,
                                                                        userName?.toString()!!,
                                                                        balance?.toString()!!.toDouble(),
                                                                        firebaseAuth.currentUser!!.uid
                                                                    )
                                                                )
                                                            )
                                                        }
                                                    }
                                                } else {
                                                    userLiveData.postValue(
                                                        Resource.error(
                                                            userTask.exception?.message.toString(),
                                                            null
                                                        )
                                                    )
                                                }
                                            }
                                    } else {
                                        userLiveData.postValue(
                                            Resource.error(
                                                "Email is not verified, check your mailinbox",
                                                null
                                            )
                                        )
                                    }
                                }
                            } else {
                                userLiveData.postValue(
                                    Resource.error(
                                        task.exception?.message.toString(),
                                        null
                                    )
                                )
                                Log.d(TAG, task.exception.toString())
                            }
                        }
                    }
                }
            }
            else -> {
                userLiveData.postValue(Resource.error("No internet connection", null))
            }
        }
        return userLiveData
    }
        fun getUserId(): String? {
            return authRepository.getUserId()
        }

//   override fun onIdTokenChanged(auth: FirebaseAuth) {
//      this.user.value = auth.currentUser
//   }
//
//   override fun onCleared(){
//      authRepository.removeUserChangeListener(this)
//      super.onCleared()
//   }

    }

//class AuthViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
//   override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//      if(modelClass.isAssignableFrom(AuthViewModel::class.java)){
//         @Suppress("UNCHECKED_CAST")
//         return AuthViewModel((application as CryptoInvestApplication).authRepository, ) as T
//      }
//      throw IllegalArgumentException("Unknown ViewModel class")
//   }
//}