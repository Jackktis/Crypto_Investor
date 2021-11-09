package com.example.cryptoinvestor.model

import com.example.cryptoinvestor.model.api.CoinCapApi
import com.example.cryptoinvestor.model.api.dto.AssetDto
import com.example.cryptoinvestor.model.api.dto.toModel
import com.example.cryptoinvestor.model.db.AppDatabase
import com.example.cryptoinvestor.model.db.toEntity
import com.example.cryptoinvestor.model.db.toModel
//import com.example.cryptoinvestor.model.dto.dto.Asset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import retrofit2.Retrofit

class AssetsRepository(
    private val coinCapApi: CoinCapApi,
    private val database: AppDatabase
) {

    data class State(
        val isInProgress: Boolean,
        val error: String? = null
    )

    private val _states = MutableStateFlow(State(isInProgress = false))
    val states = _states.asStateFlow()

    fun loadAsset(id: String): Flow<AssetDto> =
        database.assetDao()
            .loadById(id)
            .map { it?.toModel() ?: AssetDto(id, "", 0f, 0f, 0f) }

    suspend fun refreshAsset(id: String) {
        _states.value = State(isInProgress = true)
        try {
            val asset = coinCapApi.getAsset(id).body()
            val dbAsset = asset!!.toEntity()
            database.assetDao().insert(dbAsset)
            _states.value = State(isInProgress = false)
        } catch (ex: Exception) {
            _states.value = State(isInProgress = false, error = ex.message)
        }
    }
}