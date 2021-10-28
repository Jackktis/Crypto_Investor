package com.example.cryptoinvestor.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.cryptoinvestor.CryptoInvestApplication
import com.example.cryptoinvestor.model.AuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (private val authRepository: AuthRepository, firebaseAuth: FirebaseAuth) : ViewModel(){
   private val userLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()



   fun signOut() = viewModelScope.launch {
      authRepository.signOut()
   }

   fun signIn(email: String, password: String): Task<AuthResult>{
      return authRepository.signIn(email,password)
   }

   fun signUp(email: String, password: String, fullName: String, userName: String) {
      authRepository.signUp(email, password, fullName, userName)
   }

   fun getUserId() : String? {
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