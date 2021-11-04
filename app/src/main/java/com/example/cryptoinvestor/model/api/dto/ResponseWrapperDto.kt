package com.example.coincapshortc.model.api.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseWrapperDto<R>(val data: R, val timestamp: Long)
