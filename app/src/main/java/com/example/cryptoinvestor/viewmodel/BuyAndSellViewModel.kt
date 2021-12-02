package com.example.cryptoinvestor.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoinvestor.model.AuthRepository
import com.example.cryptoinvestor.model.TransactionRepository
import com.example.cryptoinvestor.model.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class BuyAndSellViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    var quantityMessageObserver : MutableLiveData<Boolean> = MutableLiveData()

    fun registerTransaction(
        coinName: String,
        symbol: String,
        totalPrice: Double,
        quantity: Double,
        tag: String
    ) {
        transactionRepository.registerTransaction(coinName, symbol, totalPrice, quantity, tag)
    }

    fun registerBuyTransaction(coinName: String, totalPrice: Double, quantity: Double) {
        userRepository.getUserBalance {
            if (it.toDouble() >= totalPrice) {
                transactionRepository.buyTransaction(coinName, totalPrice, quantity, it.toDouble())
            }
        }
    }

    fun registerSellTransaction(coinName: String, totalPrice: Double, quantity: Double) {
        viewModelScope.launch {
            try {
                //Calling repository to make network call to get the user's quantity of desired coin
                userRepository.getUserQuantity(coinName) { coinQuantity ->
                    if (coinQuantity >= quantity) {
                        //set true if the user has enough quantity to sell
                        quantityMessageObserver.value = true
                        //Calling repository to make network call to get the user's balance
                        userRepository.getUserBalance {
                            //Gets the user's balance and calls repository to make network call for making a transaction
                            transactionRepository.sellTransaction(
                                coinName,
                                totalPrice,
                                quantity,
                                it.toDouble()
                            )
                        }
                    } else {
                        //sets false if user do not have enough quantity to sell
                        quantityMessageObserver.value = false
                        println("$coinQuantity er mindre end $quantity")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}