package com.example.cryptoinvestor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoinvestor.model.AuthRepository
import com.example.cryptoinvestor.model.TransactionRepository
import com.example.cryptoinvestor.model.UserRepository
import com.example.cryptoinvestor.model.api.dto.TransactionDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val user : UserRepository,
    private val boughtInTransactionRepository: TransactionRepository
): ViewModel() {

    val userBalance: MutableLiveData<String> = MutableLiveData()
    var boughtList: MutableLiveData<List<TransactionDto>> = MutableLiveData()

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
    fun getBought() {
        viewModelScope.launch {
            try {
                boughtInTransactionRepository.getBoughtInTransaction{
                    boughtList.value = it
                }

            }catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }


}