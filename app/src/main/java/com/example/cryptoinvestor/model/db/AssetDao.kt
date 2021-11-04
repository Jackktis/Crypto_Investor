package com.example.cryptoinvestor.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {

    @Query("SELECT * FROM asset WHERE id = :id")
    fun loadById(id: String): Flow<AssetEntity?>

    @Query("SELECT * FROM asset ORDER BY price DESC")
    suspend fun loadAll(): List<AssetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asset: AssetEntity)

}