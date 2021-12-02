package com.example.cryptoinvestor.model.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

/*
    Data klasse som beskriver historikken for en Krypto-valuta
 */

@JsonClass(generateAdapter = true)
data class AssetHistoryDTO(
    @Json(name = "priceUsd")
    val priceUsd: Float,
    @Json(name = "time")
    val time: Float,
    @Json(name = "date")
    val date: String
)

fun AssetHistoryDTO.toModel(): AssetHistoryDTO = AssetHistoryDTO(priceUsd,time,date)