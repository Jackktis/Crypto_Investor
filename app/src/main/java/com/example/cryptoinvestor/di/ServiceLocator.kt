package com.example.cryptoinvestor.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.get
import com.example.cryptoinvestor.model.AssetsRepository
import com.example.cryptoinvestor.model.api.CoinCapApi
import com.example.cryptoinvestor.viewmodel.CryptoViewModel
import com.example.cryptoinvestor.model.db.AppDatabase

//import com.example.coincapshortc.ui.rates.RatesViewModel

object ServiceLocator {

    private lateinit var applicationContext: Context

    fun init(applicationContext: Context) {
        ServiceLocator.applicationContext = applicationContext
    }

    val database: AppDatabase by lazy {
        AppDatabase.build(this.applicationContext)
    }

    private val coinCapApi: CoinCapApi by lazy {
        CoinCapApi.build()
    }


    private val assetsRepository: AssetsRepository by lazy {
        AssetsRepository(coinCapApi, database)
    }

    private val viewModelFactory by lazy {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return when (modelClass) {
                    //MainViewModel::class.java -> MainViewModel(gameRepository)
                    //AssetDetailsViewModel::class.java -> AssetDetailsViewModel(assetsRepository)
                    CryptoViewModel::class.java -> CryptoViewModel(coinCapApi)
                    else -> throw IllegalArgumentException("Unsupported ViewModel $modelClass")
                } as T
            }
        }
    }

//    val ViewModelStoreOwner.mainViewModel: MainViewModel
//        get() = ViewModelProvider(this, viewModelFactory).get()


    // TODO: 04-11-2021 : Viewmodel for crypto skal kobles til service locatoren
//    val ViewModelStoreOwner.assetDetailsViewModel: AssetDetailsViewModel
//        get() = ViewModelProvider(this, viewModelFactory).get()

    val ViewModelStoreOwner.cryptoViewModel: CryptoViewModel
        get() = ViewModelProvider(this, viewModelFactory).get()
}