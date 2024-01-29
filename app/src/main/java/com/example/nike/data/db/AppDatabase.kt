package com.example.nike.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nike.data.Product
import com.example.nike.data.repo.source.productDataSource.ProductLocalDataSource

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract  class AppDatabase: RoomDatabase() {
    abstract fun productDao():ProductLocalDataSource
}