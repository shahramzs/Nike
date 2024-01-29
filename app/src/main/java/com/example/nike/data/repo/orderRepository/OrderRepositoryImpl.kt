package com.example.nike.data.repo.orderRepository

import com.example.nike.data.SubmitOrderResult
import com.example.nike.data.repo.source.orderDataSource.OrderDataSource
import io.reactivex.Single

class OrderRepositoryImpl(private val orderDataSource: OrderDataSource) : OrderRepository {
    override fun submit(
        user: String?,
        firstName: String,
        lastName: String,
        postalCode: String,
        mobile: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
        return orderDataSource.submit(
            user,
            firstName,
            lastName,
            postalCode,
            mobile,
            address,
            paymentMethod
        )
    }
}