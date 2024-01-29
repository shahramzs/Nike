package com.example.nike.feature.auth

import android.os.Bundle
import com.example.nike.R
import com.example.nike.base.NikeActivity
import com.example.nike.feature.auth.fragment.LoginFragment

class AuthActivity : NikeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer,LoginFragment())
        }.commit()
    }
}