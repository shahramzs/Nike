package com.example.nike.data

import timber.log.Timber

object TokenContainer {

      var token :String ?= null
         private set

    fun update(token:String?){
        Timber.tag("token").i(token)
        this.token = token
    }
}