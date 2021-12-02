package com.example.cryptoinvestor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoinvestor.model.UserRepository
import com.example.cryptoinvestor.model.api.CoinCapApi
import com.example.cryptoinvestor.model.api.dto.AssetDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val coinCapApi: CoinCapApi,
    private val user: UserRepository
) : ViewModel() {

    var assetsList: MutableLiveData<Response<List<AssetDto>>> = MutableLiveData()
    val userBalance: MutableLiveData<String> = MutableLiveData()

    init {
        viewModelScope.launch {
            try {
                //Network call to get user's balance
                user.getUserBalance {
                    //Using the result from our network call and setting the user's balance
                    userBalance.value = it
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun getHundredAssets() {
        viewModelScope.launch {
            try {
                //API-call to get us a response in form of a list of AssetDtos
                val response = coinCapApi.getHundredAssets()
                //assign response list to assetList
                assetsList.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}