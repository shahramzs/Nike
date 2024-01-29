package com.example.nike.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddToCart(
    val id:Int,
    val user:String,
    val code:String,
    val count:Int
) : Parcelable
