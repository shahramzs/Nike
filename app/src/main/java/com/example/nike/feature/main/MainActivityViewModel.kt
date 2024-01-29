package com.example.nike.feature.main

import com.example.nike.base.NikeSingleObserver
import com.example.nike.base.NikeViewModel
import com.example.nike.data.CartItemCount
import com.example.nike.data.TokenContainer
import com.example.nike.data.repo.cartRepository.CartRepository
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainActivityViewModel(private val cartRepository: CartRepository):NikeViewModel() {

    fun getCartItemCount(){
        if(!TokenContainer.token.isNullOrEmpty()){
            cartRepository.getCartItemsCount()
                .subscribeOn(Schedulers.io())
                .subscribe(object: NikeSingleObserver<CartItemCount>(compositeDisposable){
                    override fun onSuccess(t: CartItemCount) {
                        EventBus.getDefault().postSticky(t)
                    }

                })
        }

    }
}