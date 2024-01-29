package com.example.nike.feature.shipping

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.nike.R
import com.example.nike.base.EXTRA_KEY_DATA
import com.example.nike.base.NikeSingleObserver
import com.example.nike.base.openUrlInCustomTab
import com.example.nike.data.PurchaseDetail
import com.example.nike.data.SubmitOrderResult
import com.example.nike.data.TokenContainer
import com.example.nike.databinding.ActivityShippingBinding
import com.example.nike.feature.cart.CartItemAdapter
import com.example.nike.feature.checkout.CheckOutActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShippingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShippingBinding
    private val shippingViewModel: ShippingViewModel by viewModel()
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShippingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val purchaseDetail = intent.getParcelableExtra<PurchaseDetail>(EXTRA_KEY_DATA)
            ?: throw IllegalStateException("purchase detail can not be null.")
        val purchaseDetailView = findViewById<View>(R.id.purchaseDetailView)
        val viewHolder = CartItemAdapter.PurchaseDetailViewHolder(purchaseDetailView)
        viewHolder.bind(
            purchaseDetail.totalPrice,
            purchaseDetail.shippingCost,
            purchaseDetail.payablePrice
        )

        val onClick = View.OnClickListener {
            if(validation()) {
                shippingViewModel.submitOrder(
                    TokenContainer.token,
                    binding.firstNameEt.text.toString(),
                    binding.lastNameEt.text.toString(),
                    binding.postalCodeEt.text.toString(),
                    binding.mobileEt.text.toString(),
                    binding.addressEt.text.toString(),
                    if (it.id == R.id.onlinePaymentBtn) PAYMENT_METHOD_ONLINE else PAYMENT_METHOD_COD
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NikeSingleObserver<SubmitOrderResult>(compositeDisposable) {
                        override fun onSuccess(t: SubmitOrderResult) {
                            if (t.site.isNotEmpty()) {
                                openUrlInCustomTab(this@ShippingActivity,t.site)
                            }else{
                                startActivity(Intent(this@ShippingActivity,CheckOutActivity::class.java).apply {
                                    putExtra(EXTRA_KEY_DATA,t.code)
                                })
                            }
                            finish()
                        }

                    })
            }else{
                TODO()
            }
        }

        binding.onlinePaymentBtn.setOnClickListener(onClick)
        binding.cashOnDeliveryBtn.setOnClickListener(onClick)

    }

    private fun validation(): Boolean {
        if(binding.firstNameEt.text.isNullOrEmpty()){
            binding.firstNameEtl.error = "Please Add Your First Name."
            return false
        }else if(binding.lastNameEt.text.isNullOrEmpty()){
            binding.lastNameEtl.error = "Please Add Your Last Name."
            return false
        }else if(binding.postalCodeEt.text.isNullOrEmpty()){
            binding.postalCodeEtl.error = " Please Add Your postalCode"
            return false
        }else if(binding.mobileEt.text.isNullOrEmpty()){
            binding.mobileEtl.error = "Please Add Your Mobile"
            return false
        }else if(binding.addressEt.text.isNullOrEmpty()){
            binding.addressEtl.error = "Plase Add Your Address."
            return false
        }
        return true
    }
}