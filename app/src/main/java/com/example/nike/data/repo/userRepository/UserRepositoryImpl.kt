package com.example.nike.data.repo.userRepository

import com.example.nike.data.TokenContainer
import com.example.nike.data.TokenResponse
import com.example.nike.data.repo.source.userDataSource.UserDataSource
import io.reactivex.Completable

class UserRepositoryImpl(private val userRemoteDataSource: UserDataSource, private val userLocalDataSource: UserDataSource):UserRepository {
    override fun login(username: String, password: String): Completable {
        return userRemoteDataSource.login(username,password).doOnSuccess {
            onSuccessfulLogin(username,it)
        }.toCompletable()
    }

    override fun signup(username: String, password: String): Completable {
        return userRemoteDataSource.signup(username,password).flatMap {
            userRemoteDataSource.login(username,password)
        }.doOnSuccess {
            onSuccessfulLogin(username,it)
        }.toCompletable()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    override fun getUserName(): String = userLocalDataSource.getUserName()


    override fun signOut() {
        userLocalDataSource.signOut()
        TokenContainer.update(null)
    }

    private fun onSuccessfulLogin(username:String, tokenResponse: TokenResponse){
        TokenContainer.update(tokenResponse.token)
        userLocalDataSource.saveToken(tokenResponse.token)
        userLocalDataSource.saveUserName(username)
    }
}