package com.example.cryptoinvestor.model.dto

import java.time.LocalDateTime

class dto {

    data class User(var firstname: String,
                    var password: String,
                    var balance: Int,
                    var transactions: List<Transaction>,
                    var portfolio: Portfolio)

    data class Currency(var currencyName: String, var pricePer: Float)

    data class Transaction(
        val currency: Currency,
        var totalPrice: Float,
        var time: LocalDateTime)

    data class Portfolio(var listOfOwned: List<Currency>)
}