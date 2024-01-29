package com.example.nike.services.imageLoading

import android.widget.ImageView
import com.squareup.picasso.Picasso

class PicassoImageLoadingService:ImageLoadingServicePicasso {

    override fun loadPicasso(imageView: ImageView, imageUrl: String) {
        if(imageView is ImageView){
            Picasso.get().load(imageUrl).into(imageView)
        }else{
            throw IllegalStateException("ImageView must be instance of SimpleDraweeView")
        }
    }
}