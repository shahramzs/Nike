package com.example.nike.feature.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nike.data.Banner

class BannerSliderAdapter(fragment: Fragment, val banner:List<Banner>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = banner.size

    override fun createFragment(position: Int): Fragment = BannerFragment.newInstance(banner[position])
}