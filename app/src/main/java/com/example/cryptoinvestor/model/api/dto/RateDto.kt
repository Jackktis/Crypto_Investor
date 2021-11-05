package com.example.cryptoinvestor.model.api.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RateDto(
    val name: String? ,
    val symbol: String?,
    val currencySymbol: String?,
    val type: String?,
    val rateUsd: String?,
)