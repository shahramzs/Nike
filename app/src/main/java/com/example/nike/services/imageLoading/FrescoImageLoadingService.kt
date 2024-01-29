package com.example.nike.services.imageLoading

import com.example.nike.customView.NikeImageView
import com.facebook.drawee.view.SimpleDraweeView

class FrescoImageLoadingService:ImageLoadingService {
    override fun load(imageView: NikeImageView, imageUrl : String) {
        if(imageView is SimpleDraweeView){
            imageView.setImageURI(imageUrl)
        }else{
            throw IllegalStateException("ImageView must be instance of SimpleDraweeView")
        }
    }
}