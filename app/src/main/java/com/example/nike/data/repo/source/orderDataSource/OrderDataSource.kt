package com.example.nike.data.repo.source.orderDataSource

import com.example.nike.data.SubmitOrderResult
import io.reactivex.Single

interface OrderDataSource {

    fun submit(
        user: String?,
        firstName: String,
        lastName: String,
        postalCode: String,
        mobile: String,
        address: String,
        paymentMethod:String
    ): Single<SubmitOrderResult>
}