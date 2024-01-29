package com.example.nike.base

import androidx.annotation.StringRes

class NikeException(val type:Type,@StringRes val userFriendlyMessage:Int = 0, val serverMessage:String? = null):Throwable() {

    enum class Type{
        SIMPLE,DIALOG,AUTH
    }
}