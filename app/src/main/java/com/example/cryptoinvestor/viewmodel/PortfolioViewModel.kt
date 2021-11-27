package com.example.cryptoinvestor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoinvestor.model.AuthRepository
import com.example.cryptoinvestor.model.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val user : UserRepository
): ViewModel() {

    val userBalance: MutableLiveData<String> = MutableLiveData()

    init {
        viewModelScope.launch {
            try {
                user.getUserBalance {
                    userBalance.value = it
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}