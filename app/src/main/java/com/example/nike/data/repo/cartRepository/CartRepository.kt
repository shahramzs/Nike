package com.example.nike.data.repo.cartRepository

import com.example.nike.data.AddToCart
import com.example.nike.data.Cart
import com.example.nike.data.CartItemCount
import com.example.nike.data.MessageResponse
import io.reactivex.Single

interface CartRepository {

    fun addToCart(code:String):Single<AddToCart>

    fun get():Single<List<Cart>>

    fun remove(code:String):Single<MessageResponse>

    fun changeCount(code:String,count:Int):Single<AddToCart>

    fun getCartItemsCount():Single<CartItemCount>
}