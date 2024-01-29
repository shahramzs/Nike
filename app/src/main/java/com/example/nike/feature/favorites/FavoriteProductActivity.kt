package com.example.nike.feature.favorites

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nike.R
import com.example.nike.base.EXTRA_KEY_DATA
import com.example.nike.base.NikeActivity
import com.example.nike.data.Product
import com.example.nike.databinding.ActivityFavoriteProductBinding
import com.example.nike.feature.productDetails.ProductDetailsActivity
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteProductActivity : NikeActivity(), FavoritesProductAdapter.FavoriteProductEventListener {

    private lateinit var binding:ActivityFavoriteProductBinding

    private val favoriteViewModel : FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.helpBtn.setOnClickListener {
            Snackbar.make(it,R.string.favorite_message,Snackbar.LENGTH_LONG).show()
        }

        favoriteViewModel.favoriteProductLiveData.observe(this){
            if(it.isNotEmpty()){
                binding.favoriteProductRv.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
                binding.favoriteProductRv.adapter = FavoritesProductAdapter(it as MutableList<Product>,this,get())
            }else{
            showEmptyState(R.layout.view_default_empty_state)
                val message = findViewById<TextView>(R.id.emptyStateMessageTv)
                message.text = getString(R.string.favorite_empty_state)
            }

        }

    }

    override fun onClick(product: Product) {
        startActivity(Intent(this,ProductDetailsActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }

    override fun onLongClick(product: Product) {
        favoriteViewModel.removeFromFavorites(product)
    }
}