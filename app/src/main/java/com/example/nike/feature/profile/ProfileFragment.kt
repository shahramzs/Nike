package com.example.nike.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nike.R
import com.example.nike.base.NikeFragment
import com.example.nike.databinding.FragmentProfileBinding
import com.example.nike.feature.auth.AuthActivity
import com.example.nike.feature.favorites.FavoriteProductActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment:NikeFragment() {

    private val profileViewModel:ProfileViewModel by viewModel()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoriteProductBtn.setOnClickListener {
            startActivity(Intent(requireContext(),FavoriteProductActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        checkAuthState()
    }

    private fun checkAuthState() {
        if(profileViewModel.isSignIn){
            binding.authBtn.text = getString(R.string.signOut)
            binding.authBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.outline_exit_to_app_24,0,0,0)
            binding.usernameTv.text = profileViewModel.username
            binding.authBtn.setOnClickListener{
                profileViewModel.signOut()
                checkAuthState()
            }
        }else{
            binding.authBtn.text = getString(R.string.login)
            binding.usernameTv.text = "Guest"
            binding.authBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.person_pin,0,0,0)
            binding.authBtn.setOnClickListener {
                startActivity(Intent(requireContext(),AuthActivity::class.java))
            }
        }
    }
}