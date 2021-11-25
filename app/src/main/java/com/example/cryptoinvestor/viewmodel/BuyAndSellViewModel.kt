package com.example.cryptoinvestor.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cryptoinvestor.model.TransactionRepository

class BuyAndSellViewModel(
    private val transactionRepository: TransactionRepository
    ) : ViewModel() {

    fun registerBuyTransaction(coinName : String, totalPrice : Double, quantity : Double) {
        transactionRepository.buyTransaction(coinName, totalPrice, quantity)
    }


}