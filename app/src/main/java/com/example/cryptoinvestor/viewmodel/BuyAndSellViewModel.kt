package com.example.cryptoinvestor.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cryptoinvestor.model.AuthRepository
import com.example.cryptoinvestor.model.TransactionRepository
import com.example.cryptoinvestor.model.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BuyAndSellViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
    ) : ViewModel() {

    fun registerTransaction(coinName: String, totalPrice : Double, quantity : Double, tag : String){
        transactionRepository.registerTransaction(coinName,totalPrice,quantity,tag)
    }

    fun registerBuyTransaction(coinName : String, totalPrice : Double, quantity : Double) {
        userRepository.getUserBalance {
            if (it.toDouble() >= totalPrice){
                transactionRepository.buyTransaction(coinName, totalPrice, quantity, it.toDouble())
            }
        }
    }

    fun registerSellTransaction(coinName : String, totalPrice : Double, quantity : Double){
        transactionRepository.sellTransaction(coinName, totalPrice, quantity)
    }


}