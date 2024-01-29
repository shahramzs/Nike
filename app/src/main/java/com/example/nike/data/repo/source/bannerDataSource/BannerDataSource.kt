package com.example.nike.data.repo.source.bannerDataSource

import com.example.nike.data.Banner
import io.reactivex.Single

interface BannerDataSource {

    fun getBanners():Single<List<Banner>>
}