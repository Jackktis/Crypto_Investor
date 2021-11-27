package com.example.cryptoinvestor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoinvestor.model.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val auth : AuthRepository
): ViewModel() {

    val userBalance: MutableLiveData<String> = MutableLiveData()

    init {
        viewModelScope.launch {
            try {
                auth.getUserBalance {
                    userBalance.value = it
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}