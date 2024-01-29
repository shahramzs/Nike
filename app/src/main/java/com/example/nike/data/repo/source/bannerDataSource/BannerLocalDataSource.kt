package com.example.nike.data.repo.source.bannerDataSource

import com.example.nike.data.Banner
import io.reactivex.Single

class BannerLocalDataSource:BannerDataSource {
    override fun getBanners(): Single<List<Banner>> {
        TODO("Not yet implemented")
    }
}