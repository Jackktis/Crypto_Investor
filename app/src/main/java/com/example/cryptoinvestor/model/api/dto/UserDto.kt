package com.example.cryptoinvestor.model.api.dto

/*
    Data klasse som beskriver en bruger.
 */
    data class User(
        val email: String = "",
        val fullName: String = "",
        val userName: String = "",
        var balance: Double? = null,
        val userId: String = ""
    )