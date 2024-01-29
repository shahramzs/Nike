package com.example.nike.data.repo.source.productDataSource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.nike.data.Product
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProductLocalDataSource: ProductDataSource {
    override fun getProduct(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM products")
    override fun getFavoriteProducts(): Single<List<Product>>

    @Insert
    override fun addToFavorites(product: Product): Completable

    @Delete
    override fun deleteFromFavorites(product: Product): Completable
}