package com.example.nike.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    val id:Int,
    val code:String,
    val title:String,
    val name:String,
    val comment:String,
    val time:String
) : Parcelable
