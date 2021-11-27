package com.example.cryptoinvestor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoinvestor.model.AuthRepository
import com.example.cryptoinvestor.model.UserRepository
import com.example.cryptoinvestor.model.api.CoinCapApi
import com.example.cryptoinvestor.model.api.dto.AssetDto
import dagger.hilt.android.lifecycle.HiltViewModel
//import com.example.cryptoinvestor.model.dto.dto
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/*
       Overvej om denne klasse ikke bare skal indholde forskellige metoder til at hente coins fra Api'ens metoder
       Eksempelvis en masse metoder som getTenAssets() med et viewModelScope.launch i.
       Hvad gør init?- Sikkert skide smart, men kunne ikke få det til at virke

 */
@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val coinCapApi: CoinCapApi,
    private val user: UserRepository
) : ViewModel() {

    var assetsList: MutableLiveData<Response<List<AssetDto>>> = MutableLiveData()
    val userBalance: MutableLiveData<String> = MutableLiveData()
//    val assets: MutableLiveData<AssetDto>
//        get() = _assets

    init {
        /*
        Her bliver bitcoin kaldet på, vi skal nok have en liste af alle Assets.
         */
        viewModelScope.launch {
            try {
                //network call to get users balance
                user.getUserBalance {
                    //Using the result from our network call and setting the user's balance
                    userBalance.value = it
                    println("Balance value from viewModel: $it")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun getTenAssets() {
        viewModelScope.launch {
            try {
                val response = coinCapApi.getTenAssets()
                assetsList.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}