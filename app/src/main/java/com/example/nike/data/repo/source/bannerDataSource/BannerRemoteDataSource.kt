package com.example.nike.data.repo.source.bannerDataSource

import com.example.nike.data.Banner
import com.example.nike.services.http.ApiService
import io.reactivex.Single

class BannerRemoteDataSource(private val apiService: ApiService):BannerDataSource {
    override fun getBanners(): Single<List<Banner>> = apiService.getBanner()
}