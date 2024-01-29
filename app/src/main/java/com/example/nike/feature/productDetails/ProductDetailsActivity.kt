package com.example.nike.feature.productDetails

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nike.R
import com.example.nike.base.EXTRA_KEY_ID
import com.example.nike.base.NikeActivity
import com.example.nike.base.NikeCompletableObserver
import com.example.nike.base.formatPrice
import com.example.nike.customView.NikeImageView
import com.example.nike.customView.scroll.ObservableScrollView
import com.example.nike.customView.scroll.ObservableScrollViewCallbacks
import com.example.nike.customView.scroll.ScrollState
import com.example.nike.data.Comment
import com.example.nike.feature.productDetails.comments.CommentListActivity
import com.example.nike.services.imageLoading.ImageLoadingService
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductDetailsActivity : NikeActivity() {
    private val productDetailViewModel: ProductDetailsViewModel by viewModel { parametersOf(intent.extras) }
    private val imageLoadingService: ImageLoadingService by inject()
    private val commentAdapter = CommentAdapter()
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        EventBus.getDefault().register(this)

        val viewAllComments:MaterialButton = findViewById(R.id.viewAllCommentsBtn)
        val productIv: NikeImageView = findViewById(R.id.productDetailsIv)
        val titleTv: TextView = findViewById(R.id.titleTv)
        val previousPriceTv: TextView = findViewById(R.id.previous_priceTv)
        val currentPriceTv: TextView = findViewById(R.id.current_priceTv)
        val description: TextView = findViewById(R.id.details_description)
        val toolbarView: MaterialCardView = findViewById(R.id.toolbarView)
        val back: ImageView = findViewById(R.id.back_btn)
        val favorite: ImageView = findViewById(R.id.favorite_btn)
        val toolbarTitleTv: TextView = findViewById(R.id.toolbarTitleTv)
        val observableScrollView: ObservableScrollView = findViewById(R.id.observableScrollView)
        val commentRv: RecyclerView = findViewById(R.id.commentRv)
        val addToCartBtn:ExtendedFloatingActionButton = findViewById(R.id.addToCartBtn)

        productDetailViewModel.productLiveData.observe(this) {
            imageLoadingService.load(productIv, it.image)
            titleTv.text = it.title
            previousPriceTv.text = formatPrice(it.previousPrice)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            currentPriceTv.text = formatPrice(it.price)
            description.text = it.description
            toolbarTitleTv.text = it.title
        }

       productDetailViewModel.progressbarLiveData.observe(this){
           setProgressIndicator(it)
       }
        productDetailViewModel.commentLiveData.observe(this) {
            Timber.tag("comment").i(it.toString())
            if(it[0].id == 0){
                commentRv.visibility = View.GONE
            }
            if(it.size > 3){
                viewAllComments.visibility = View.VISIBLE
                viewAllComments.setOnClickListener{
                    startActivity(Intent(this, CommentListActivity::class.java).apply {
                        putExtra(EXTRA_KEY_ID,productDetailViewModel.productLiveData.value!!.code)
                    })
                }
            }
            commentAdapter.comments = it as ArrayList<Comment>
        }

        commentRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        commentRv.adapter = commentAdapter

        productIv.post {
            val productIvHeight = productIv.height
            observableScrollView.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {
                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean
                ) {
                    toolbarView.alpha = scrollY.toFloat() / productIvHeight.toFloat() - 0.3f
                    productIv.translationY = scrollY.toFloat() / 2
                    if (scrollY == 0) {
                        toolbarView.alpha = 0f
                    }
                }

                override fun onDownMotionEvent() {

                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

                }

            })
        }

        addToCartBtn.setOnClickListener{
            productDetailViewModel.onAddToCart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        showSnackBar(getString(R.string.added_to_cart),Snackbar.LENGTH_LONG)
                    }

                })
        }

    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
        compositeDisposable.clear()
    }

}