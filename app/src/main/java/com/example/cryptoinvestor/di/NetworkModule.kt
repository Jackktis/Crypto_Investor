package com.example.cryptoinvestor.di

import com.example.cryptoinvestor.model.api.CoinCapApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun proviceCoinCapApi() : CoinCapApi{
        return CoinCapApi.build()
    }
}