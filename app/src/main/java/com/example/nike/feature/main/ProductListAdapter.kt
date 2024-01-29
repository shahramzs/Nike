package com.example.nike.feature.main

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nike.R
import com.example.nike.base.formatPrice
import com.example.nike.base.implementSpringAnimationTrait
import com.example.nike.customView.NikeImageView
import com.example.nike.data.Product
import com.example.nike.services.imageLoading.ImageLoadingService

const val VIEW_TYPE_ROUND = 0
const val VIEW_TYPE_SMALL = 1
const val VIEW_TYPE_LARGE = 2

class ProductListAdapter(var viewType:Int = VIEW_TYPE_ROUND, val imageLoadingService: ImageLoadingService): RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var productEventListener:ProductEventListener?=null

    var products = ArrayList<Product>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutResId = when(viewType){
            VIEW_TYPE_ROUND -> R.layout.item_product
            VIEW_TYPE_SMALL -> R.layout.item_product_small
            VIEW_TYPE_LARGE -> R.layout.item_product_large
            else -> throw IllegalStateException("view type must be detected.")
        }
       return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutResId,parent,false))
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  = holder.bindProduct(products[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTv = itemView.findViewById<TextView>(R.id.productTitleTv)
        private val currentPrice = itemView.findViewById<TextView>(R.id.currentPriceTv)
        private val previousPrice = itemView.findViewById<TextView>(R.id.previousPriceTv)
        private val productIv = itemView.findViewById<NikeImageView>(R.id.productIv)
        private val favorite = itemView.findViewById<ImageView>(R.id.favoriteBtn)

        fun bindProduct(product: Product){
            imageLoadingService.load(productIv,product.image)
            titleTv.text = product.title
            currentPrice.text = formatPrice(product.price)
            previousPrice.text =  formatPrice(product.previousPrice)
            previousPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener{
            productEventListener?.onProductClick(product)
            }

            if(product.isFavorite){
                favorite.setImageResource(R.drawable.baseline_favorite_24_fill)
            }else{
                favorite.setImageResource(R.drawable.ic_favorites)
            }
            favorite.setOnClickListener {
                productEventListener?.onFavoriteBtnClick(product)
                product.isFavorite = !product.isFavorite
                notifyItemChanged(adapterPosition)
            }
        }
    }

    interface ProductEventListener{
        fun onProductClick(product:Product)
        fun onFavoriteBtnClick(product: Product)
    }
}