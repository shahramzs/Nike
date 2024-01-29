package com.example.nike.data.repo.cartRepository

import com.example.nike.data.AddToCart
import com.example.nike.data.Cart
import com.example.nike.data.CartItemCount
import com.example.nike.data.CartResponse
import com.example.nike.data.MessageResponse
import com.example.nike.data.repo.source.cartDataSource.CartDataSource
import io.reactivex.Single

class CartRepositoryImpl(private val cartRemoteDataSource: CartDataSource):CartRepository {
    override fun addToCart(code: String): Single<AddToCart> = cartRemoteDataSource.addToCart(code)

    override fun get(): Single<List<Cart>> = cartRemoteDataSource.get()

    override fun remove(code: String): Single<MessageResponse> = cartRemoteDataSource.remove(code)

    override fun changeCount(code: String, count: Int): Single<AddToCart> = cartRemoteDataSource.changeCount(code,count)

    override fun getCartItemsCount(): Single<CartItemCount> = cartRemoteDataSource.getCartItemsCount()
}