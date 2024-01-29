package com.example.nike.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubmitOrderResult(
    val id:Int,
    val code:String,
    val site:String
) : Parcelable
