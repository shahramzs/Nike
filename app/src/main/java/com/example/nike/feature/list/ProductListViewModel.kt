package com.example.nike.feature.list

import androidx.lifecycle.MutableLiveData
import com.example.nike.R
import com.example.nike.base.NikeCompletableObserver
import com.example.nike.base.NikeSingleObserver
import com.example.nike.base.NikeViewModel
import com.example.nike.base.asyncNetworkRequest
import com.example.nike.data.Product
import com.example.nike.data.repo.productRepository.ProductRepository
import io.reactivex.schedulers.Schedulers

class ProductListViewModel(var sort:Int, private val productRepository: ProductRepository):NikeViewModel() {

    val productLiveData = MutableLiveData<List<Product>>()
    val selectedSortTitleLiveData = MutableLiveData<Int>()
    val sortTitles = arrayOf(R.string.sortLatest, R.string.sortPopular, R.string.sortPriceHighToLow, R.string.sortPriceLowToHigh)
    init {
        getProduct()
        selectedSortTitleLiveData.value = sortTitles[sort]
    }
    private fun getProduct(){
        progressbarLiveData.value = true
        productRepository.getProduct()
            .asyncNetworkRequest()
            .doFinally { progressbarLiveData.value = false }
            .subscribe(object: NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    productLiveData.value = t
                }

            })
    }

    fun onSelectedSortChangeByUser(sort:Int){
        this.sort = sort
        this.selectedSortTitleLiveData.value = sortTitles[sort]
        getProduct()
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
                .subscribe(object: NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        product.isFavorite = true
                    }

                })
        }
    }
}