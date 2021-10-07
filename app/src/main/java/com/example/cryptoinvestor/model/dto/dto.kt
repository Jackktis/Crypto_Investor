package com.example.cryptoinvestor.model.dto

import java.time.LocalDateTime

class dto {

    data class User(
        var username: String,
        var firstname: String,
        var password: String,
        var balance: Int,
        var transactions: List<Transaction>,
        var portfolio: Portfolio)

    data class Currency(val currencyID: String, val symbol: String, val currencyName: String, var pricePer: Float, var favorite: Boolean)

    data class Transaction(
        val currencyID: String,
        val currency: Currency,
        var totalPrice: Float,
        var quantity: Float,
        var time: LocalDateTime)

    data class Portfolio(var listOfOwned: List<Currency>)
}