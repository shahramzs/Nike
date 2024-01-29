package com.example.nike.data.repo.orderRepository

import com.example.nike.data.SubmitOrderResult
import io.reactivex.Single

interface OrderRepository {

    fun submit(
        user: String?,
        firstName: String,
        lastName: String,
        postalCode: String,
        mobile: String,
        address: String,
        paymentMethod:String
    ):Single<SubmitOrderResult>

}