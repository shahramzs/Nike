package com.example.nike.data.repo.source.cartDataSource

import com.example.nike.data.AddToCart
import com.example.nike.data.Cart
import com.example.nike.data.CartItemCount
import com.example.nike.data.MessageResponse
import com.example.nike.data.TokenContainer
import com.example.nike.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class CartRemoteDataSource(private val apiService: ApiService) : CartDataSource {
    override fun addToCart(code: String): Single<AddToCart> = apiService.addToCart(
        JsonObject().apply {
            addProperty("user", TokenContainer.token)
            addProperty("code", code)
            addProperty("count", 1)
        }
    )

    override fun get(): Single<List<Cart>> = apiService.getCart(TokenContainer.token.toString())

    override fun remove(code: String): Single<MessageResponse> = apiService.removeItemFromCart(
        JsonObject().apply {
            addProperty("user", TokenContainer.token)
            addProperty("code", code)
        })

    override fun changeCount(code: String, count: Int): Single<AddToCart> = apiService.changeCount(
        JsonObject().apply {
            addProperty("user",TokenContainer.token)
            addProperty("code", code)
            addProperty("count",count)
        }
    )

    override fun getCartItemsCount(): Single<CartItemCount> = apiService.getCartItemCount(JsonObject().apply {
        addProperty("user",TokenContainer.token)
    })
}