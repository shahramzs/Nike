package com.example.nike.feature.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nike.R
import com.example.nike.customView.NikeImageView
import com.example.nike.data.Product
import com.example.nike.services.imageLoading.ImageLoadingService

class FavoritesProductAdapter(val products:MutableList<Product>, val favoriteProductEventListener: FavoriteProductEventListener, val imageLoadingService: ImageLoadingService): RecyclerView.Adapter<FavoritesProductAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<NikeImageView>(R.id.nikeImageView)
        private val title = itemView.findViewById<TextView>(R.id.productTitleTv)

        fun bind(product: Product){
            title.text = product.title
            imageLoadingService.load(image,product.image)
            itemView.setOnClickListener {
                favoriteProductEventListener.onClick(product)
            }

            itemView.setOnLongClickListener{
                products.remove(product)
                notifyItemRemoved(adapterPosition)
                favoriteProductEventListener.onLongClick(product)
                product.isFavorite = false
                return@setOnLongClickListener false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_product,parent,false))
    }

    override fun getItemCount(): Int  = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    interface FavoriteProductEventListener{

        fun onClick(product: Product)

        fun onLongClick(product: Product)
    }
}