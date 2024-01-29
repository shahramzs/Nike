package com.example.nike.data.repo.source.userDataSource

import com.example.nike.data.DetailResponse
import com.example.nike.data.TokenResponse
import com.example.nike.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class UserRemoteDataSource(private val apiService: ApiService):UserDataSource {
    override fun login(username: String, password: String): Single<TokenResponse> {
        return apiService.login(JsonObject().apply {
            addProperty("username",username)
            addProperty("password",password)
        })
    }

    override fun signup(username: String, password: String): Single<DetailResponse> {
        return apiService.signup(JsonObject().apply {
            addProperty("username",username)
            addProperty("password",password)
        })
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String) {
        TODO("Not yet implemented")
    }

    override fun saveUserName(username: String) {
        TODO("Not yet implemented")
    }

    override fun getUserName(): String {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }
}