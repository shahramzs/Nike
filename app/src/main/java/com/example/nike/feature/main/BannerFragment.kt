package com.example.nike.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.nike.R
import com.example.nike.base.EXTRA_KEY_DATA
import com.example.nike.customView.NikeImageView
import com.example.nike.data.Banner
import com.example.nike.services.imageLoading.ImageLoadingService
import com.example.nike.services.imageLoading.ImageLoadingServicePicasso
import org.koin.android.ext.android.inject

class BannerFragment: Fragment() {

    private val imageLoadingService:ImageLoadingService by inject()
    private val imageLoadingServicePicasso:ImageLoadingServicePicasso by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val imageView = inflater.inflate(R.layout.fragment_banner,container,false) as NikeImageView
        val imageView2 = inflater.inflate(R.layout.fragment_banner2,container,false) as ImageView
        val banner = requireArguments().getParcelable<Banner>(EXTRA_KEY_DATA)?: throw IllegalStateException("Banner can not be null")
        imageLoadingService.load(imageView,banner.banner)
        imageLoadingServicePicasso.loadPicasso(imageView2,banner.banner)
        return imageView
    }

    companion object{
        fun newInstance(banner: Banner):BannerFragment {
           return BannerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_KEY_DATA,banner)
                }
            }
        }
    }
}