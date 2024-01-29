package com.example.nike.data.repo.source.userDataSource

import com.example.nike.data.DetailResponse
import com.example.nike.data.TokenResponse
import io.reactivex.Single

interface UserDataSource {

    fun login(username:String, password:String): Single<TokenResponse>

    fun signup(username: String,password: String): Single<DetailResponse>

    fun loadToken()

    fun saveToken(token:String)

    fun saveUserName(username:String)

    fun getUserName():String

    fun signOut()
}