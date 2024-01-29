package com.example.nike.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Banner(
    val id:Int,
    val code:String,
    val banner:String,
    val linkType:Int,
    val linkValue:Int
):Parcelable