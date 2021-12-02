package com.example.cryptoinvestor.viewmodel

import androidx.lifecycle.*
import com.example.cryptoinvestor.model.api.CoinCapApi
import com.example.cryptoinvestor.model.api.dto.AssetDto
import com.example.cryptoinvestor.model.api.dto.AssetHistoryDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class InfoCryptoViewModel @Inject constructor(private val coinCapApi: CoinCapApi) : ViewModel() {
    val asset: MutableLiveData<Response<AssetDto>> = MutableLiveData()
    var assetHistory: MutableLiveData<Response<List<AssetHistoryDTO>>> = MutableLiveData()

    fun refreshAsset(assetId: String) {
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

    fun getAssetHistory(assetId: String) {
        viewModelScope.launch {
            try {
                val response = coinCapApi.getAssetHistory(assetId)
                assetHistory.value = response
                println("RESPONSE: $response")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}