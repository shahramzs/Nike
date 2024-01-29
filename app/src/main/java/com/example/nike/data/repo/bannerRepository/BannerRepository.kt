package com.example.nike.data.repo.bannerRepository

import com.example.nike.data.Banner
import io.reactivex.Single

interface BannerRepository {
    fun getBanner(): Single<List<Banner>>
}