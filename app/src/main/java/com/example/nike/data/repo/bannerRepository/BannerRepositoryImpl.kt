package com.example.nike.data.repo.bannerRepository

import com.example.nike.data.Banner
import com.example.nike.data.repo.source.bannerDataSource.BannerDataSource
import com.example.nike.data.repo.source.bannerDataSource.BannerLocalDataSource
import io.reactivex.Single

class BannerRepositoryImpl(private val bannerRemoteDataSource: BannerDataSource, val bannerLocalDataSource: BannerLocalDataSource):BannerRepository {
    override fun getBanner(): Single<List<Banner>> = bannerRemoteDataSource.getBanners()
}