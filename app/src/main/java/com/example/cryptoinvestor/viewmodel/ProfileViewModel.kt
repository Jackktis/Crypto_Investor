package com.example.cryptoinvestor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoinvestor.model.AuthRepository
import com.example.cryptoinvestor.model.TransactionRepository
import com.example.cryptoinvestor.model.UserRepository
import com.example.cryptoinvestor.model.api.dto.AssetDto
import com.example.cryptoinvestor.model.api.dto.TransactionDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    val userBalance: MutableLiveData<String> = MutableLiveData()
    var transactionList: MutableLiveData<List<TransactionDto>> = MutableLiveData()

    init {
        viewModelScope.launch {
            try {
                //network call to get users balance
                userRepository.getUserBalance {
                    //Using the result from our network call and setting the user's balance
                    userBalance.value = it
                    println("Balance value from viewModel: $it")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getTransaction() {
        viewModelScope.launch {
           try {
               transactionRepository.getTransaction{
                   transactionList.value = it
               }

           }catch (e: Exception) {
               e.printStackTrace()
           }

        }
    }

}