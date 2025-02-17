package com.example.qr_scanner.data

import androidx.compose.ui.text.style.LineBreak
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Query("SELECT * FROM products_2")
    fun getAllProducts(): LiveData<List<Product>>


    @Query("DELETE FROM products_2 WHERE id = :id")
    suspend fun deleteProduct(id: Int)



}