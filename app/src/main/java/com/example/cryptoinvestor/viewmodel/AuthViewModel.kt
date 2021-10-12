package com.example.cryptoinvestor.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cryptoinvestor.CryptoInvestApplication
import com.example.cryptoinvestor.model.AuthRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel(), FirebaseAuth.IdTokenListener {
   val user: MutableLiveData<FirebaseUser?> = MutableLiveData()

   init {
       authRepository.addUserChangeListener(this)
   }



   fun signOut() = viewModelScope.launch {
      authRepository.signOut()
   }

   fun signIn(email: String, password: String){
      authRepository.signInWithEmailAndPassword()
   }

   override fun onIdTokenChanged(auth: FirebaseAuth) {
      this.user.value = auth.currentUser
   }

   override fun onCleared(){
      authRepository.removeUserChangeListener(this)
      super.onCleared()
   }

}

class AuthViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
   override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      if(modelClass.isAssignableFrom(AuthViewModel::class.java)){
         @Suppress("UNCHECKED_CAST")
         return AuthViewModel((application as CryptoInvestApplication).authRepository) as T
      }
      throw IllegalArgumentException("Unknown ViewModel class")
   }

}