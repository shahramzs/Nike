package com.example.nike.data.repo.source.productDataSource

import com.example.nike.data.Product
import com.example.nike.services.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(private val apiService: ApiService): ProductDataSource {

    override fun getProduct(): Single<List<Product>> = apiService.getProduct()

    override fun getFavoriteProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorites(product: Product): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorites(product: Product): Completable {
        TODO("Not yet implemented")
    }
}