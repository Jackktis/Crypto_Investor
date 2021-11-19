package com.example.cryptoinvestor.viewmodel

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.example.cryptoinvestor.model.api.CoinCapApi
import com.example.cryptoinvestor.model.api.dto.AssetDto
import com.example.cryptoinvestor.model.api.dto.AssetHistoryDTO
import com.example.cryptoinvestor.utils.FLOAT_FORMATTER
import com.example.cryptoinvestor.utils.INTEGER_PRICE_FORMATTER
import com.example.cryptoinvestor.utils.PRICE_FORMATTER
import com.example.cryptoinvestor.view.adapter.RateAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.currency_list.view.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime

class InfoCryptoViewModel(private val coinCapApi: CoinCapApi) : ViewModel() {
    //val _asset: MutableLiveData<Response<AssetDto>> = MutableLiveData()


    val asset:  MutableLiveData<Response<AssetDto>> = MutableLiveData()
    var assetHistory: MutableLiveData<Response<List<AssetHistoryDTO>>> = MutableLiveData()

//    val name: LiveData<String> = asset.map{it.name}
//    val symbol: LiveData<String> = asset.map {it.symbol}
//    val price: LiveData<String> = asset.map { PRICE_FORMATTER.format(it.price) }
//    val volume: LiveData<String> = asset.map { INTEGER_PRICE_FORMATTER.format(it.volume24Hr) }
//    val change: LiveData<String> = asset.map { FLOAT_FORMATTER.format(it.change24Hr) + "%" }
//    val updatedAt: LiveData<String> = asset.map { LocalTime.now().toString() }

    fun refreshAsset(assetId : String) {
        println(assetId)
        viewModelScope.launch {
            try {
                val response = coinCapApi.getAsset(assetId)

                asset.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAssetHistory(assetId: String){
        viewModelScope.launch {
            try {
                val response = coinCapApi.getAssetHistory(assetId)
                assetHistory.value = response
                println("RESPONSE: $response")
            }catch (e: Exception){
                e.printStackTrace()
            }
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
