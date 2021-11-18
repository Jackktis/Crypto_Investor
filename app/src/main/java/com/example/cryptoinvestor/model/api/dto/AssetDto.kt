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
    @Json(name = "id")
    val id: String,
    @Json(name= "rank")
    val rank: String,
    @Json(name = "symbol")
    val symbol: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "supply")
    val supply: Float,
    @Json(name = "maxSupply")
    val maxSupply: Float?,
    @Json(name = "marketCapUsd")
    val marketCapUsd: Float,
    @Json(name = "volumeUsd24Hr")
    val volume24Hr: Float,
    @Json(name = "priceUsd")
    val price: Float,
    @Json(name = "changePercent24Hr")
    val change24Hr: Float,
    @Json(name = "vwap24Hr")
    val vwap24Hr : Float?

)

fun AssetDto.toModel(): AssetDto =
    AssetDto(id, rank, symbol, name, supply, maxSupply, marketCapUsd, volume24Hr, price, change24Hr, vwap24Hr)

