package com.example.cryptoinvestor.model.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

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