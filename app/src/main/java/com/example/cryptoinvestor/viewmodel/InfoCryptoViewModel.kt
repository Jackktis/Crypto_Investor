package com.example.cryptoinvestor.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import com.example.cryptoinvestor.model.api.CoinCapApi
import com.example.cryptoinvestor.model.api.dto.AssetDto
import com.example.cryptoinvestor.utils.FLOAT_FORMATTER
import com.example.cryptoinvestor.utils.INTEGER_PRICE_FORMATTER
import com.example.cryptoinvestor.utils.PRICE_FORMATTER
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime

class InfoCryptoViewModel(coinCapApi: CoinCapApi) : ViewModel() {
    //val _asset: MutableLiveData<Response<AssetDto>> = MutableLiveData()


    val asset:  MutableLiveData<Response<AssetDto>> = MutableLiveData()
//    val name: LiveData<String> = asset.map{it.name}
//    val symbol: LiveData<String> = asset.map {it.symbol}
//    val price: LiveData<String> = asset.map { PRICE_FORMATTER.format(it.price) }
//    val volume: LiveData<String> = asset.map { INTEGER_PRICE_FORMATTER.format(it.volume24Hr) }
//    val change: LiveData<String> = asset.map { FLOAT_FORMATTER.format(it.change24Hr) + "%" }
//    val updatedAt: LiveData<String> = asset.map { LocalTime.now().toString() }
    var assetName : String = ""
    fun getCoinDetails (name : String) {
        assetName = name
    }

    init {
        viewModelScope.launch {
            try {
                val response = coinCapApi.getAsset(assetName)

                asset.value = response

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

//    fun getCoinInfo(name : String) {
//        coinCapApi.getRateAsString(name).enqueue(object : Callback<String> {
//            override fun onResponse(
//                call: Call<String>,
//                response: Response<String>
//            ) {
//                //_assets.value = listOf()
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                //   _assets.value = listOf()
//            }
//        })
//    }



}