package com.example.cryptoinvestor.model.api

import com.squareup.moshi.Types
import com.example.coincapshortc.model.api.dto.ResponseWrapperDto
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class WrapperConverter : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val wrappedType = Types.newParameterizedType(ResponseWrapperDto::class.java, type)
        val delegate = retrofit.nextResponseBodyConverter<ResponseWrapperDto<*>>(
            this,
            wrappedType,
            annotations
        )
        return Converter { body -> delegate.convert(body)?.data }
    }
}