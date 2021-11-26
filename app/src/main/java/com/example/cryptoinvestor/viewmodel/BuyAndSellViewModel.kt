package com.example.cryptoinvestor.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cryptoinvestor.model.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BuyAndSellViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
    ) : ViewModel() {

    fun registerTransaction(coinName: String, totalPrice : Double, quantity : Double, tag : String){
        transactionRepository.registerTransaction(coinName,totalPrice,quantity,tag)
    }

    fun registerBuyTransaction(coinName : String, totalPrice : Double, quantity : Double) {
        transactionRepository.buyTransaction(coinName, totalPrice, quantity)
    }

    fun registerSellTransaction(coinName : String, totalPrice : Double, quantity : Double){
        transactionRepository.sellTransaction(coinName, totalPrice, quantity)
    }


}