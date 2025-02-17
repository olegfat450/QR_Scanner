package com.example.qr_scanner.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase







@Database(entities = [Product::class], version = 1_0)

abstract class MainDb: RoomDatabase() {

    abstract val dao: com.example.qr_scanner.data.Dao
}