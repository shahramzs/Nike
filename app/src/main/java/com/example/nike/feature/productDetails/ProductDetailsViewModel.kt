package com.example.nike.feature.productDetails

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.nike.base.EXTRA_KEY_DATA
import com.example.nike.base.NikeSingleObserver
import com.example.nike.base.NikeViewModel
import com.example.nike.base.asyncNetworkRequest
import com.example.nike.data.Comment
import com.example.nike.data.Product
import com.example.nike.data.repo.cartRepository.CartRepository
import com.example.nike.data.repo.commentRepository.CommentRepository
import io.reactivex.Completable

class ProductDetailsViewModel(private val bundle: Bundle, private val commentRepository: CommentRepository, private val cartRepository: CartRepository) : NikeViewModel() {

     val productLiveData = MutableLiveData<Product>()
     val commentLiveData = MutableLiveData<List<Comment>>()
    init{
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
        progressbarLiveData.value = true
        commentRepository.getAll(productLiveData.value!!.code)
            .asyncNetworkRequest()
            .doFinally{progressbarLiveData.value = false}
            .subscribe(object: NikeSingleObserver<List<Comment>>(compositeDisposable){
                override fun onSuccess(t: List<Comment>) {
                    commentLiveData.value = t
                }

            })
    }

    fun onAddToCart():Completable = cartRepository.addToCart(productLiveData.value!!.code).toCompletable()
}