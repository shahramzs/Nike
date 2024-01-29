package com.example.nike.feature.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nike.R
import com.example.nike.base.EXTRA_KEY_DATA
import com.example.nike.base.NikeCompletableObserver
import com.example.nike.base.NikeFragment
import com.example.nike.data.Cart
import com.example.nike.data.Product
import com.example.nike.feature.auth.AuthActivity
import com.example.nike.feature.productDetails.ProductDetailsActivity
import com.example.nike.feature.shipping.ShippingActivity
import com.example.nike.services.http.Api
import com.example.nike.services.imageLoading.ImageLoadingService
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CartFragment: NikeFragment(),CartItemAdapter.CartItemCallbacks {

    val cartViewModel : CartViewModel by viewModel()
    var cartItemAdapter:CartItemAdapter ?=null
    val imageLoadingService:ImageLoadingService by inject()
    val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cartItemRv = view.findViewById<RecyclerView>(R.id.cartItemsRv)
        val payBtn = view.findViewById<ExtendedFloatingActionButton>(R.id.payBtn)

        cartViewModel.progressbarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }

        cartViewModel.cartItemsLiveData.observe(viewLifecycleOwner){
            Timber.tag("cart").i(it.toString())
            cartItemRv.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            cartItemAdapter = CartItemAdapter(it as MutableList<Cart>, imageLoadingService,this)
            cartItemRv.adapter = cartItemAdapter
        }

        cartViewModel.purchaseDetailLiveData.observe(viewLifecycleOwner){
            Timber.tag("purchase").i(it.toString())
            cartItemAdapter?.let{adapter ->
                adapter.purchaseDetail = it
                adapter.notifyItemChanged(adapter.cart.size)
            }
        }

        cartViewModel.emptyStateLiveData.observe(viewLifecycleOwner){
            if(it.mustShow){
                val emptyState = showEmptyState(R.layout.view_cart_empty_state)
                emptyState?.let {view->
                    view.findViewById<TextView>(R.id.emptyStateMessageTv).text = getString(it.messageResTd)
                    view.findViewById<MaterialButton>(R.id.emptyStateCallToActionBtn).visibility = if(it.mustShowCallToActionButton) View.VISIBLE else View.GONE
                    view.findViewById<MaterialButton>(R.id.emptyStateCallToActionBtn).setOnClickListener{
                        startActivity(Intent(requireContext(),AuthActivity::class.java))
                    }
                }
            }else{
                val emptyStateRoot = requireActivity().findViewById<View>(R.id.emptyStateRootView)
                emptyStateRoot?.visibility = View.GONE
            }
        }

        payBtn.setOnClickListener{
            startActivity(Intent(requireContext(),ShippingActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA,cartViewModel.purchaseDetailLiveData.value)
            })
        }
    }

    override fun onStart() {
        super.onStart()
        cartViewModel.refresh()
    }

    override fun onRemoveCartItemButtonClick(cart: Cart) {
        cartViewModel.removeItemFromCart(cart)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartItemAdapter?.removeCartItem(cart)
                }

            })
    }

    override fun onIncreaseCartItemButtonClick(cart: Cart) {
        cartViewModel.increaseCartItemCount(cart)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartItemAdapter?.changeCount(cart)
                }

            })
    }

    override fun onDecreaseCartItemButtonClick(cart: Cart) {
        cartViewModel.decreaseCartItemCount(cart)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartItemAdapter?.changeCount(cart)
                }

            })
    }

    override fun onProductImageClick(cart: Cart) {
        val product = Product(cart.discount,cart.id,cart.code,cart.price,cart.previousPrice,cart.description,
            Api.Companion.ROOT_URL + cart.image,cart.status, cart.title)
        startActivity(Intent(requireContext(),ProductDetailsActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }
}