package com.example.qr_scanner.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products_2")
data class Product( @PrimaryKey(autoGenerate = true)
    val id: Int? = null,var value: Int = 1,val QR: String) {
}