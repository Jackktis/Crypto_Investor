package com.example.cryptoinvestor.model.dto

import java.time.LocalDateTime

class dto {

    data class User(var firstname: String,
                    var password: String,
                    var balance: Int = 10000,
                    var transactions: List<Transaction>)

    data class Currency(var currencyName: String, var pricePer: Float)

    data class Transaction(
        val currency: Currency,
        var totalPrice: Float,
        var time: LocalDateTime)

    data class UserPortfolio(val user: User, var listOfOwned: List<Currency>)
}