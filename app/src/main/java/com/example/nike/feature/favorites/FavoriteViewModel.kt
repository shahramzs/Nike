package com.example.nike.feature.favorites

import androidx.lifecycle.MutableLiveData
import com.example.nike.base.NikeCompletableObserver
import com.example.nike.base.NikeSingleObserver
import com.example.nike.base.NikeViewModel
import com.example.nike.data.Product
import com.example.nike.data.repo.productRepository.ProductRepository
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoriteViewModel(private val productRepository: ProductRepository):NikeViewModel() {

    val favoriteProductLiveData = MutableLiveData<List<Product>>()

    init{
        productRepository.getFavoriteProducts()
            .subscribeOn(Schedulers.io())
            .subscribe(object: NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    favoriteProductLiveData.postValue(t)
                }

            })
    }
    fun removeFromFavorites(product: Product){
        productRepository.deleteFromFavorites(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object: NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                   Timber.tag("Favorites").i("Favorites Deleted.")
                }

            })
    }
}