package com.example.nike.feature.cart

import androidx.lifecycle.MutableLiveData
import com.example.nike.R
import com.example.nike.base.NikeSingleObserver
import com.example.nike.base.NikeViewModel
import com.example.nike.base.asyncNetworkRequest
import com.example.nike.data.Cart
import com.example.nike.data.CartItemCount
import com.example.nike.data.EmptyState
import com.example.nike.data.Purchase
import com.example.nike.data.PurchaseDetail
import com.example.nike.data.TokenContainer
import com.example.nike.data.repo.cartRepository.CartRepository
import io.reactivex.Completable
import org.greenrobot.eventbus.EventBus
import timber.log.Timber


class CartViewModel(private val cartRepository: CartRepository) : NikeViewModel() {

    val cartItemsLiveData = MutableLiveData<List<Cart>>()
    val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
    val emptyStateLiveData = MutableLiveData<EmptyState>()

    private fun getCartItems() {
        if (!TokenContainer.token.isNullOrEmpty()) {
            emptyStateLiveData.value = EmptyState(false)
            progressbarLiveData.value = true
            cartRepository.get()
                .asyncNetworkRequest()
                .doFinally { progressbarLiveData.value = false }
                .subscribe(object : NikeSingleObserver<List<Cart>>(compositeDisposable) {
                    override fun onSuccess(t: List<Cart>) {
                        if (t.isNotEmpty()) {
                            cartItemsLiveData.value = t
                            var totalPrice = 0
                            var payablePrice = 0
                            t.forEach {
                                totalPrice += it.price * it.count
                                payablePrice += (it.price - it.discount) * it.count
                            }
                            Purchase.totalPrice = totalPrice
                            Purchase.payablePrice = payablePrice
                            purchaseDetailLiveData.value = PurchaseDetail(
                                Purchase.payablePrice,
                                Purchase.shippingCost,
                                Purchase.totalPrice
                            )
                        }else{
                            emptyStateLiveData.value = EmptyState(true, R.string.cart_empty_state)

                        }
                    }


                })
        }else{
            emptyStateLiveData.value = EmptyState(true, R.string.empty_state_message,true)
        }
    }

    fun removeItemFromCart(cart: Cart): Completable = cartRepository.remove(cart.code)
        .doAfterSuccess {
            Timber.tag("cartItemCount").i(cartItemsLiveData.value?.size.toString())
            calculateAndPublishPurchaseDetail()

            val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
            cartItemCount?.let{
                it.count -= cart.count
                EventBus.getDefault().postSticky(it)
            }

            cartItemsLiveData.value?.let {
                if(it.isEmpty()){
                    emptyStateLiveData.postValue(EmptyState(true, R.string.cart_empty_state))
                }
            }
        }
        .toCompletable()

    fun increaseCartItemCount(cart: Cart): Completable =
        cartRepository.changeCount(cart.code, ++cart.count)
            .doAfterSuccess {
                calculateAndPublishPurchaseDetail()
                val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                cartItemCount?.let{
                    it.count += 1
                    EventBus.getDefault().postSticky(it)
                }
            }
            .toCompletable()

    fun decreaseCartItemCount(cart: Cart): Completable =
        cartRepository.changeCount(cart.code, --cart.count)
            .doAfterSuccess {
                calculateAndPublishPurchaseDetail()
                val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                cartItemCount?.let{
                    it.count -= 1
                    EventBus.getDefault().postSticky(it)
                }
            }
            .toCompletable()

    fun refresh() {
        getCartItems()
    }

    private fun calculateAndPublishPurchaseDetail() {
        cartItemsLiveData.value?.let { cartItems ->
            purchaseDetailLiveData.value?.let { purchaseDetail ->
                var totalPrice = 0
                var payablePrice = 0
                cartItems.forEach {
                    totalPrice += it.price * it.count
                    payablePrice += (it.price - it.discount) * it.count
                }
                purchaseDetail.totalPrice = totalPrice
                purchaseDetail.payablePrice = payablePrice
                purchaseDetailLiveData.postValue(purchaseDetail)
            }
        }
    }
}