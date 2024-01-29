package com.example.nike.feature.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.nike.R
import com.example.nike.base.NikeCompletableObserver
import com.example.nike.feature.auth.AuthViewModel
import com.google.android.material.button.MaterialButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: Fragment() {

    private val viewmodel:AuthViewModel by viewModel()
    val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginBtn = view.findViewById<MaterialButton>(R.id.loginBtn)
        val signupBtn = view.findViewById<MaterialButton>(R.id.signupLinkBtn)
        val username = view.findViewById<EditText>(R.id.loginUsernameEt)
        val password = view.findViewById<EditText>(R.id.loginPasswordEt)

        loginBtn.setOnClickListener{
            viewmodel.login(username.text.toString(),password.text.toString()).
            subscribeOn(Schedulers.io()).
            observeOn(AndroidSchedulers.mainThread()).
            subscribe(object: NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    requireActivity().finish()
                }

            })
        }
        signupBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer,SignUpFragment())
            }.commit()
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}