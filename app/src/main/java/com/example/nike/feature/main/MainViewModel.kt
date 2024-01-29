package com.example.nike.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nike.base.NikeCompletableObserver
import com.example.nike.base.NikeSingleObserver
import com.example.nike.base.NikeViewModel
import com.example.nike.data.Banner
import com.example.nike.data.Product
import com.example.nike.data.repo.bannerRepository.BannerRepository
import com.example.nike.data.repo.productRepository.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val productRepository: ProductRepository, private val bannerRepository: BannerRepository): NikeViewModel() {

    private val _productLiveData = MutableLiveData<List<Product>>()
    val productLiveData : LiveData<List<Product>>
        get() = _productLiveData

    val bannerLiveData = MutableLiveData<List<Banner>>()

    init{
        progressbarLiveData.value = true

        productRepository.getProduct()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally{ progressbarLiveData.value = false}
            .subscribe(object: NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    _productLiveData.value = t
                }

            })

        bannerRepository.getBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: NikeSingleObserver<List<Banner>>(compositeDisposable){
                override fun onSuccess(t: List<Banner>) {
                    bannerLiveData.value = t
                }

            })
    }

    fun addProductToFavorite(product: Product){
        if(product.isFavorite){
            productRepository.deleteFromFavorites(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object: NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        product.isFavorite = false
                    }

                })
        }else{
            productRepository.addToFavorites(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object:NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        product.isFavorite = true
                    }

                })
        }
    }
}