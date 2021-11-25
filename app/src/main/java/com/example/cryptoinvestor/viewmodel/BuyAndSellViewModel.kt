package com.example.cryptoinvestor.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cryptoinvestor.model.TransactionRepository

class BuyAndSellViewModel(
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