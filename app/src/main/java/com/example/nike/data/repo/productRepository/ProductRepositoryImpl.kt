package com.example.nike.data.repo.productRepository

import com.example.nike.data.Product
import com.example.nike.data.repo.source.productDataSource.ProductDataSource
import com.example.nike.data.repo.source.productDataSource.ProductLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    private val remoteDataSource: ProductDataSource,
    private val localDataSource: ProductLocalDataSource
) :
    ProductRepository {

    override fun getProduct(): Single<List<Product>> = localDataSource.getFavoriteProducts()
        .flatMap {favoriteProduct->
            remoteDataSource.getProduct().doOnSuccess {
                val favoriteProductCode = favoriteProduct.map {codes->
                    codes.code
                }
                it.forEach { product ->
                   if(favoriteProductCode.contains(product.code))
                       product.isFavorite = true
                }
            }
        }
    override fun getFavoriteProducts(): Single<List<Product>> =
        localDataSource.getFavoriteProducts()

    override fun addToFavorites(product: Product): Completable =
        localDataSource.addToFavorites(product)

    override fun deleteFromFavorites(product: Product): Completable =
        localDataSource.deleteFromFavorites(product)
}