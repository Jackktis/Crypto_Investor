package com.example.cryptoinvestor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CryptoViewModel : ViewModel() {
    val assets: LiveData<List<CharSequence>>
        get() = _assets
    private val _assets = MutableLiveData<List<CharSequence>>()

    // TODO: 04-11-2021 : Alt nedenfor er kopieret fra shortcuts repo -
    // det skal omskrives til vores

//    init {
//        coinCapApi.getRateAsString("bitcoin").enqueue(object : Callback<String> {
//            override fun onResponse(
//                call: Call<String>,
//                response: Response<String>
//            ) {
//                _rates.value = listOf()
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                _rates.value = listOf()
//            }
//        })
//
//        viewModelScope.launch {
//            try {
//                val response = coinCapApi.getRate("bitcoin")
//            } catch (e: Exception) {
//
//            }
//        }
//
//        coinCapApi.getRates().enqueue(object : Callback<List<RateDto>> {
//            override fun onResponse(
//                call: Call<List<RateDto>>,
//                response: Response<List<RateDto>>
//            ) {
//                _rates.value = listOf()
//            }
//
//            override fun onFailure(call: Call<List<RateDto>>, t: Throwable) {
//                _rates.value = listOf()
//            }
//        })
//    }
}