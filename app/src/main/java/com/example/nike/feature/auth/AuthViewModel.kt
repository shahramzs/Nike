package com.example.nike.feature.auth

import com.example.nike.base.NikeViewModel
import com.example.nike.data.repo.userRepository.UserRepository
import io.reactivex.Completable

class AuthViewModel(private val userRepository: UserRepository):NikeViewModel() {

    fun login(username:String, password:String):Completable{
        progressbarLiveData.value = true
        return userRepository.login(username,password).doFinally{
            progressbarLiveData.postValue(false)
        }
    }

    fun signup(username:String,password:String):Completable{
        progressbarLiveData.value = true
        return userRepository.signup(username,password).doFinally {
            progressbarLiveData.postValue(false)
        }
    }
}