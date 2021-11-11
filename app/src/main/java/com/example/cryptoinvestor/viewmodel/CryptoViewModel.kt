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
/*
       Overvej om denne klasse ikke bare skal indholde forskellige metoder til at hente coins fra Api'ens metoder
       Eksempelvis en masse metoder som getTenAssets() med et viewModelScope.launch i.
       Hvad gør init?- Sikkert skide smart, men kunne ikke få det til at virke

 */
class CryptoViewModel(private val coinCapApi: CoinCapApi, assetsRepo: AssetsRepository ) : ViewModel() {
    var assetsList: MutableLiveData<Response<List<AssetDto>>> = MutableLiveData()
    val _assets:  MutableLiveData<Response<AssetDto>> = MutableLiveData()
//    val assets: MutableLiveData<AssetDto>
//        get() = _assets

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

    fun onItemClick(position: Int) {

    }
}