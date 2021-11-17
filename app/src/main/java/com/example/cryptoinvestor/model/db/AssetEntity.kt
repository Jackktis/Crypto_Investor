package com.example.cryptoinvestor.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptoinvestor.model.api.dto.AssetDto


@Entity(tableName = "asset")
data class AssetEntity(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(name = "symbol") val symbol: String,
    val price: Float,
    @ColumnInfo(name = "change_24") val change24Hr: Float,
    @ColumnInfo(name = "volume_24", defaultValue = "0") val volume24Hr: Float,
)

fun AssetEntity.toModel(): AssetDto =
    AssetDto(id, name, symbol, price, volume24Hr, change24Hr)

fun AssetDto.toEntity(): AssetEntity =
    AssetEntity(id, name, symbol, price, change24Hr, volume24Hr)
