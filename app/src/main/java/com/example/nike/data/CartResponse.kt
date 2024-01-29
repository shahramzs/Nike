package com.example.nike.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartResponse(
    val cartItems: List<Cart>,
//    val payablePrice:Int,
//    val shippingCost:Int = 0,
//    val totalPrice:Int
) : Parcelable

object Purchase{
    var payablePrice:Int = 0
    var shippingCost:Int = 0
    var totalPrice:Int = 0

}

@Parcelize
data class PurchaseDetail(
    var payablePrice:Int,
    var shippingCost:Int = 0,
    var totalPrice:Int
) : Parcelable