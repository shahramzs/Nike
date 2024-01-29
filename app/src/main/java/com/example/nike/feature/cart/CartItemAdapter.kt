package com.example.nike.feature.cart

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nike.R
import com.example.nike.base.formatPrice
import com.example.nike.customView.NikeImageView
import com.example.nike.data.Cart
import com.example.nike.data.PurchaseDetail
import com.example.nike.services.http.Api
import com.example.nike.services.imageLoading.ImageLoadingService
import com.google.android.material.button.MaterialButton

const val VIEW_TYPE_CART_ITEM = 0
const val VIEW_TYPE_PURCHASE_DETAILS = 1

class CartItemAdapter(
    val cart: MutableList<Cart>,
    val imageLoadingService: ImageLoadingService,
    val cartItemCallbacks: CartItemCallbacks
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var purchaseDetail:PurchaseDetail ?= null

    inner class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val productTitle = itemView.findViewById<TextView>(R.id.productTitleTv)
        val productImage = itemView.findViewById<NikeImageView>(R.id.productIv)
        val cartPreviousPrice = itemView.findViewById<TextView>(R.id.cartPreviousPriceTv)
        val cartPriceTv = itemView.findViewById<TextView>(R.id.cartPriceTv)
        val cartItemCount = itemView.findViewById<TextView>(R.id.cartItemCountTv)
        val increase = itemView.findViewById<ImageView>(R.id.increaseBtn)
        val decrease = itemView.findViewById<ImageView>(R.id.decreaseBtn)
        val removeFromCart = itemView.findViewById<MaterialButton>(R.id.removeFromCart)
        val progressBar = itemView.findViewById<ProgressBar>(R.id.changeCountProgressBar)

        fun bindCartItems(cart: Cart){
            productTitle.text = cart.title
            cartPreviousPrice.text = formatPrice(cart.previousPrice)
            cartPreviousPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            cartPriceTv.text = formatPrice(cart.price)
            cartItemCount.text = cart.count.toString()
            imageLoadingService.load(productImage, Api.Companion.ROOT_URL + cart.image)

            removeFromCart.setOnClickListener{
                cartItemCallbacks.onRemoveCartItemButtonClick(cart)
            }

            progressBar.visibility = if(cart.changeCountProgressBarIsVisible) View.VISIBLE else View.GONE

            cartItemCount.visibility = if(cart.changeCountProgressBarIsVisible) View.INVISIBLE else View.VISIBLE

            increase.setOnClickListener{
                cart.changeCountProgressBarIsVisible = true
                progressBar.visibility = View.VISIBLE
                cartItemCount.visibility = View.INVISIBLE
                cartItemCallbacks.onIncreaseCartItemButtonClick(cart)
            }

            decrease.setOnClickListener{
                if(cart.count > 1) {
                    cart.changeCountProgressBarIsVisible = true
                    progressBar.visibility = View.VISIBLE
                    cartItemCount.visibility = View.INVISIBLE
                    cartItemCallbacks.onDecreaseCartItemButtonClick(cart)
                }
            }

            productImage.setOnClickListener {
                cartItemCallbacks.onProductImageClick(cart)
            }
        }
    }

     class PurchaseDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val totlaPrice = itemView.findViewById<TextView>(R.id.totalPriceTv)
        val shippingCost = itemView.findViewById<TextView>(R.id.shippingCostTv)
        val payablePrice = itemView.findViewById<TextView>(R.id.payablePriceTv)

        fun bind(total_price:Int, shipping_cost:Int, payable_price:Int){
            totlaPrice.text = formatPrice(total_price)
            shippingCost.text = formatPrice(shipping_cost)
            payablePrice.text = formatPrice(payable_price)
        }
    }

    interface CartItemCallbacks{
        fun onRemoveCartItemButtonClick(cart: Cart)
        fun onIncreaseCartItemButtonClick(cart: Cart)
        fun onDecreaseCartItemButtonClick(cart: Cart)
        fun onProductImageClick(cart: Cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == VIEW_TYPE_CART_ITEM){
            return CartItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cart,parent,false))
        }else{
            return PurchaseDetailViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_purchase_detail,parent,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CartItemViewHolder)
            holder.bindCartItems(cart[position])
        else if(holder is PurchaseDetailViewHolder)
            purchaseDetail?.let {
                holder.bind(it.totalPrice,it.shippingCost,it.payablePrice)
            }
    }

    override fun getItemCount(): Int  = cart.size + 1

    override fun getItemViewType(position: Int): Int {
        return if(position == cart.size){
            VIEW_TYPE_PURCHASE_DETAILS
        }else{
            VIEW_TYPE_CART_ITEM
        }
    }

    fun removeCartItem(cartItem: Cart){
        val index = cart.indexOf(cartItem)
        if(index > -1){
            cart.removeAt(index)
            notifyItemRemoved(index)
        }
    }
    fun changeCount(cartItem:Cart){
        val index = cart.indexOf(cartItem)
        if(index > -1){
            cart[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }
}