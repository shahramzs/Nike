package com.example.nike.data.repo.source.userDataSource

import android.content.SharedPreferences
import com.example.nike.data.DetailResponse
import com.example.nike.data.TokenContainer
import com.example.nike.data.TokenResponse
import io.reactivex.Single

class UserLocalDataSource(private val sharedPreferences: SharedPreferences) : UserDataSource {
    override fun login(username: String, password: String): Single<TokenResponse> {
        TODO("Not yet implemented")
    }

    override fun signup(username: String, password: String): Single<DetailResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(sharedPreferences.getString("token", null))
    }

    override fun saveToken(token: String) {
        sharedPreferences.edit().apply {
            putString("token", token)
        }.apply()
    }

    override fun saveUserName(username: String) {
        sharedPreferences.edit().apply {
            putString("username", username)
        }.apply()
    }

    override fun getUserName(): String = sharedPreferences.getString("username", "") ?: ""

    override fun signOut() = sharedPreferences.edit().apply {
        clear()
    }.apply()

}