package com.example.cryptoinvestor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoinvestor.model.api.CoinCapApi
import com.example.cryptoinvestor.model.api.dto.RateDto
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CryptoViewModel(coinCapApi: CoinCapApi) : ViewModel() {
    val assets: LiveData<List<CharSequence>>
        get() = _assets
    private val _assets = MutableLiveData<List<CharSequence>>()

    // TODO: 04-11-2021 : Alt nedenfor er kopieret fra shortcuts repo -
    // det skal omskrives til vores

    init {
        coinCapApi.getRateAsString("bitcoin").enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                _assets.value = listOf()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                _assets.value = listOf()
            }
        })

        viewModelScope.launch {
            try {
                val response = coinCapApi.getRate("bitcoin")
            } catch (e: Exception) {

            }
        }

        coinCapApi.getRates().enqueue(object : Callback<List<RateDto>> {
            override fun onResponse(
                call: Call<List<RateDto>>,
                response: Response<List<RateDto>>
            ) {
                _assets.value = listOf()
            }

            override fun onFailure(call: Call<List<RateDto>>, t: Throwable) {
                _assets.value = listOf()
            }
        })
    }
}