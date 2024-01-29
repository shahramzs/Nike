package com.example.nike.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.nike.R
import com.example.nike.base.EXTRA_KEY_DATA
import com.example.nike.base.NikeFragment
import com.example.nike.base.convertDpToPixel
import com.example.nike.data.Product
import com.example.nike.data.SORT_LATEST
import com.example.nike.feature.list.ProductListActivity
import com.example.nike.feature.productDetails.ProductDetailsActivity
import com.google.android.material.button.MaterialButton
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class HomeFragment: NikeFragment(),ProductListAdapter.ProductEventListener {

    private val mainViewModel : MainViewModel by viewModel()
    private val productListAdapter : ProductListAdapter by inject{ parametersOf(VIEW_TYPE_ROUND) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewAllProduct:MaterialButton = view.findViewById(R.id.viewAllProduct)

        val latestProductRv = view.findViewById<RecyclerView>(R.id.latestProductsRv)
        latestProductRv.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        latestProductRv.adapter = productListAdapter

        productListAdapter.productEventListener = this

        viewAllProduct.setOnClickListener{
            startActivity(Intent(requireContext(),ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_LATEST)
            })
        }

        mainViewModel.productLiveData.observe(viewLifecycleOwner) {
            Timber.tag("product").i(it.toString())
            productListAdapter.products = it as ArrayList<Product>
        }

        mainViewModel.progressbarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }

        mainViewModel.bannerLiveData.observe(viewLifecycleOwner){
            Timber.tag("banner").i(it.toString())

            val bannerViewPager = view.findViewById<ViewPager2>(R.id.bannerSliderViewPager)
            val dotsIndicator = view.findViewById<DotsIndicator>(R.id.dots_indicator);
            val bannerSliderAdapter = BannerSliderAdapter(this,it)
            bannerViewPager?.adapter = bannerSliderAdapter
            dotsIndicator.attachTo(bannerViewPager)
            val viewPagerHeight = ((bannerViewPager.measuredWidth - convertDpToPixel(32f,requireContext())) * 173) / 320
            Timber.tag("width").i(viewPagerHeight.toString())
            val layoutParams = bannerViewPager.layoutParams
            layoutParams.height = viewPagerHeight.toInt()
            bannerViewPager.layoutParams = layoutParams
        }
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(requireContext(),ProductDetailsActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }

    override fun onFavoriteBtnClick(product: Product) {
        mainViewModel.addProductToFavorite(product)
    }
}