package com.example.nike.feature.profile

import com.example.nike.base.NikeViewModel
import com.example.nike.data.CartItemCount
import com.example.nike.data.TokenContainer
import com.example.nike.data.repo.userRepository.UserRepository
import org.greenrobot.eventbus.EventBus

class ProfileViewModel(private val userRepository: UserRepository):NikeViewModel() {
    val username: String
        get() = userRepository.getUserName()

    val isSignIn:Boolean
        get() = TokenContainer.token != null

    fun signOut() {
        userRepository.signOut()
        badgeStatus()
    }

    private fun badgeStatus(){
        val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
        EventBus.getDefault().postSticky(cartItemCount)
    }
}