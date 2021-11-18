package com.example.cryptoinvestor.model.api

import com.example.cryptoinvestor.model.api.dto.AssetDto
import com.example.cryptoinvestor.model.api.dto.RateDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import timber.log.Timber

interface CoinCapApi {
    @GET("rates")
    fun getRates(): Call<List<AssetDto>>

    @GET("rates/{id}")
    suspend fun getRate(@Path("id") id: String): RateDto

    @GET("rates/{id}")
    fun getRateAsString(@Path("id") id: String): Call<String>

    @GET("v2/assets/{id}")
    suspend fun getAsset(@Path("id") id: String): Response<AssetDto>

    /*
        "v2" er tilføjet her da baseUrlen ikke gav den videre, mærkeligt problem?
    */
    @GET("v2/assets/?limit=10")
    suspend fun getTenAssets(): Response<List<AssetDto>>

    companion object {
        fun build(): CoinCapApi =
            Retrofit.Builder()
                //tilføjelse af v2 virker ikke her?
                .baseUrl("https://api.coincap.io/")
                .client(OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        chain.request().newBuilder()
                            .addHeader(
                                "Authorization",
                                "Bearer 2df561e8-7ba7-4ea3-85bf-f7a2b620d19b"
                            )
                            .build()
                            .let { chain.proceed(it) }
                    }
                    .addInterceptor(
                        HttpLoggingInterceptor({ Timber.d(it) })
                            .also { it.level = HttpLoggingInterceptor.Level.BODY }
                    )
                    .build()
                )
                .addConverterFactory(WrapperConverter())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create()
    }
}