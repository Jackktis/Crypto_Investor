package com.example.cryptoinvestor.di

import com.example.cryptoinvestor.model.api.CoinCapApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
    Firebase Hilt Module class that is installed in a Hilt Component
    The module component type is SingletonComponent, which means anything in the application can use it.
    The scope annotation of the functions are set to Singleton, to scope the binding to the SingletonComponent
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun proviceCoinCapApi() : CoinCapApi{
        return CoinCapApi.build()
    }
}