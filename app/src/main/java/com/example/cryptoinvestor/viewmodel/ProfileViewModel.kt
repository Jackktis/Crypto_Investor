package com.example.cryptoinvestor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoinvestor.model.AuthRepository
import com.example.cryptoinvestor.model.TransactionRepository
import com.example.cryptoinvestor.model.api.dto.AssetDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: AuthRepository
) : ViewModel() {
    val userBalance: MutableLiveData<String> = MutableLiveData()

    init {
        viewModelScope.launch {
            try {
                //network call to get users balance
                auth.getUserBalance {
                    //Using the result from our network call and setting the user's balance
                    userBalance.value = it
                    println("Balance value from viewModel: $it")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}