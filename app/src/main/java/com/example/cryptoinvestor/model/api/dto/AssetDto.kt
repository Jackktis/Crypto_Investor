package com.example.cryptoinvestor.model.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/*
    Denne klasse bliver pt brugt til at lave coinobjekter, skal denne bruges fremfor Rate?
    Hvad er forskellen?
    Evt. skal vi lave en CoinDto, som er vores egen, så vi ikke har alle de her forskellige dtoer.
 */
@JsonClass(generateAdapter = true)
data class AssetDto(
    val id: String,
    val name: String,
    @Json(name = "symbol")
    val symbol: String,
    @Json(name = "priceUsd")
    val price: Float,
    @Json(name = "volumeUsd24Hr")
    val volume24Hr: Float,
    @Json(name = "changePercent24Hr")
    val change24Hr: Float,
)

fun AssetDto.toModel(): AssetDto =
    AssetDto(id, name, symbol, price, volume24Hr, change24Hr)

