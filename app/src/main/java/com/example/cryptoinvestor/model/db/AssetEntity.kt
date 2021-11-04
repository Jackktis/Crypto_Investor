package com.example.cryptoinvestor.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptoinvestor.model.dto.dto.Asset

@Entity(tableName = "asset")
data class AssetEntity(
    @PrimaryKey val id: String,
    val name: String,
    val price: Float,
    @ColumnInfo(name = "change_24") val change24Hr: Float,
    @ColumnInfo(name = "volume_24", defaultValue = "0") val volume24Hr: Float,
)

fun AssetEntity.toModel(): Asset =
    Asset(id, name, price, volume24Hr, change24Hr)

fun Asset.toEntity(): AssetEntity =
    AssetEntity(id, name, price, change24Hr, volume24Hr)
