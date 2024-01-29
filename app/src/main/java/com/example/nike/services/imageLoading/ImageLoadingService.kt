package com.example.nike.services.imageLoading

import com.example.nike.customView.NikeImageView

interface ImageLoadingService {
    fun load(imageView: NikeImageView, imageUrl:String)
}

