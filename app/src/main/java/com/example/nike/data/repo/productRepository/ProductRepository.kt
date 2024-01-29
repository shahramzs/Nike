package com.example.nike.data.repo.productRepository

import com.example.nike.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getProduct(): Single<List<Product>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addToFavorites(product: Product): Completable

    fun deleteFromFavorites(product: Product): Completable
}