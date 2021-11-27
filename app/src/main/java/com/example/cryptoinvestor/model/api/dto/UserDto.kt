package com.example.cryptoinvestor.model.api.dto

    data class User(
        val email: String = "",
        val fullName: String = "",
        val userName: String = "",
        var balance: Double? = null,
        val userId: String = ""
        //var transactions: List<Transaction>,
        //var portfolio: Portfolio
    )