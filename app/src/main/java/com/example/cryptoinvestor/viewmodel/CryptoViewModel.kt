package com.example.cryptoinvestor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoinvestor.model.AssetsRepository
import com.example.cryptoinvestor.model.api.CoinCapApi
import com.example.cryptoinvestor.model.api.dto.AssetDto
import com.example.cryptoinvestor.model.api.dto.RateDto
//import com.example.cryptoinvestor.model.dto.dto
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CryptoViewModel(private val coinCapApi: CoinCapApi, assetsRepo: AssetsRepository ) : ViewModel() {
//    val assets: MutableLiveData<AssetDto>
//        get() = _assets
    val _assets:  MutableLiveData<Response<AssetDto>> = MutableLiveData()
    var assetsList: MutableLiveData<Response<List<AssetDto>>> = MutableLiveData()

    // TODO: 04-11-2021 : Alt nedenfor er kopieret fra shortcuts repo -
    // det skal omskrives til vores

    init {
        coinCapApi.getRateAsString("bitcoin").enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
               //_assets.value = listOf()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
             //   _assets.value = listOf()
            }
        })
        /*
        Her bliver bitcoin kaldet på, vi skal nok have en liste af alle Assets.
         */
        viewModelScope.launch {
            try {
                val response = coinCapApi.getAsset("bitcoin")

                _assets.value = response

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        coinCapApi.getRates().enqueue(object : Callback<List<AssetDto>> {
            override fun onResponse(
                call: Call<List<AssetDto>>,
                response: Response<List<AssetDto>>
            ) {
                //assetsList.value =
            }

            override fun onFailure(call: Call<List<AssetDto>>, t: Throwable) {
                //_assets.value = listOf()
            }

        })

//        viewModelScope.launch {
//            try {
//                val response = coinCapApi.getTenAssets()
//                assetsList.value = response
//            } catch (e: Exception){
//                e.printStackTrace()
//            }
//        }

    }
    fun getTenAssets(){
        viewModelScope.launch {
            try {
                val response = coinCapApi.getTenAssets()
                assetsList.value = response
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}