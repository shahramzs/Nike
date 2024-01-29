package com.example.nike.services.http

import com.example.nike.data.AddToCart
import com.example.nike.data.Banner
import com.example.nike.data.Cart
import com.example.nike.data.CartItemCount
import com.example.nike.data.Comment
import com.example.nike.data.DetailResponse
import com.example.nike.data.MessageResponse
import com.example.nike.data.Product
import com.example.nike.data.SubmitOrderResult
import com.example.nike.data.TokenContainer
import com.example.nike.data.TokenResponse
import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET(Api.PRODUCT_URL)
    fun getProduct(): Single<List<Product>>

    @GET(Api.BANNER_URL)
    fun getBanner(): Single<List<Banner>>

    @GET(Api.COMMENT_URL + "{code}")
    fun getComment(@Path("code") code:String):Single<List<Comment>>

    @POST(Api.ADD_TO_CART)
    fun addToCart(@Body jsonObject: JsonObject):Single<AddToCart>

    @POST(Api.DELETE_CART)
    fun removeItemFromCart(@Body jsonObject: JsonObject):Single<MessageResponse>

    @GET(Api.LIST_CART + "{user}")
    fun getCart(@Path("user") user:String):Single<List<Cart>>

    @POST(Api.CHANGE_COUNT)
    fun changeCount(@Body jsonObject: JsonObject):Single<AddToCart>

    @POST(Api.GET_CART_ITEM_COUNT)
    fun getCartItemCount(@Body jsonObject: JsonObject):Single<CartItemCount>

    @POST(Api.AUTH)
    fun login(@Body jsonObject: JsonObject):Single<TokenResponse>

    @POST(Api.REGISTER)
    fun signup(@Body jsonObject: JsonObject):Single<DetailResponse>

    @POST(Api.SHIPPING)
    fun submitOrder(@Body jsonObject: JsonObject):Single<SubmitOrderResult>
}

fun createApiServiceInstance():ApiService{

    val okHttpClient = OkHttpClient.Builder()
    .addInterceptor{
        val oldRequest = it.request()
        val newRequest = oldRequest.newBuilder()
        if(TokenContainer.token != null)
            newRequest.addHeader("Authorization","Token ${TokenContainer.token}")

        newRequest.addHeader("Accept","application/json")
        newRequest.method(oldRequest.method, oldRequest.body)
        return@addInterceptor it.proceed(newRequest.build())
    }
    .addInterceptor(HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    })
    .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(Api.ROOT_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
    return retrofit.create(ApiService::class.java)
}