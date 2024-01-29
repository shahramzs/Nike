package com.example.nike.feature.shipping

import com.example.nike.base.NikeViewModel
import com.example.nike.data.SubmitOrderResult
import com.example.nike.data.repo.orderRepository.OrderRepository
import io.reactivex.Single

const val PAYMENT_METHOD_COD = "cash_on_delivery"
const val PAYMENT_METHOD_ONLINE = "online"

class ShippingViewModel(private val orderRepository: OrderRepository) : NikeViewModel() {

    fun submitOrder(
        user: String?,
        firstName: String,
        lastName: String,
        postalCode: String,
        mobile: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult>{
        return orderRepository.submit(user,firstName, lastName, postalCode, mobile, address, paymentMethod)
    }
}