package com.example.nike.data.repo.source.orderDataSource

import com.example.nike.data.SubmitOrderResult
import com.example.nike.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class OrderRemoteDataSource(private val apiService: ApiService):OrderDataSource {
    override fun submit(
        user: String?,
        firstName: String,
        lastName: String,
        postalCode: String,
        mobile: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult>  = apiService.submitOrder(JsonObject().apply {
        addProperty("user",user)
        addProperty("firstName",firstName)
        addProperty("lastName",lastName)
        addProperty("postalCode",postalCode)
        addProperty("mobile",mobile)
        addProperty("address",address)
        addProperty("paymentMethod",paymentMethod)
    })
}