package com.example.nike.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart(
    val user:String,
    var count:Int,
    val discount: Int,
    val id: Int,
    val code: String,
    val price: Int,
    val previousPrice:Int,
    val image:String,
    val status: Int,
    val title: String,
    val description:String,

    var changeCountProgressBarIsVisible: Boolean = false,

) : Parcelable
