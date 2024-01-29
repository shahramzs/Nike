package com.example.nike.feature.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nike.R
import com.example.nike.base.EXTRA_KEY_DATA
import com.example.nike.base.NikeActivity
import com.example.nike.customView.NikeToolbar
import com.example.nike.data.Product
import com.example.nike.feature.main.ProductListAdapter
import com.example.nike.feature.main.VIEW_TYPE_LARGE
import com.example.nike.feature.main.VIEW_TYPE_SMALL
import com.example.nike.feature.productDetails.ProductDetailsActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductListActivity : NikeActivity(),ProductListAdapter.ProductEventListener {
    private val productListViewModel : ProductListViewModel by viewModel { parametersOf(intent.extras!!.getInt(
        EXTRA_KEY_DATA)) }

    private val productListAdapter:ProductListAdapter by inject{ parametersOf(VIEW_TYPE_SMALL) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
         val viewTypeChanger:ImageView = findViewById(R.id.viewTypeChangerBtn)
        val sortProductRv = findViewById<RecyclerView>(R.id.sortProductRv)
        val sortBtn: View = findViewById(R.id.sortBtn)
        val selectedSortText:TextView = findViewById(R.id.sortTitleText)
        val toolbarView:NikeToolbar = findViewById(R.id.toolbarView)

        val gridLayoutManager = GridLayoutManager(this,2)
        sortProductRv.layoutManager = gridLayoutManager
        sortProductRv.adapter = productListAdapter

        viewTypeChanger.setOnClickListener{
            if(productListAdapter.viewType == VIEW_TYPE_SMALL){
                viewTypeChanger.setImageResource(R.drawable.ic_plus_square)
                productListAdapter.viewType = VIEW_TYPE_LARGE
                gridLayoutManager.spanCount = 1
                productListAdapter.notifyDataSetChanged()
            }else{
                viewTypeChanger.setImageResource(R.drawable.ic_grid)
                productListAdapter.viewType = VIEW_TYPE_SMALL
                gridLayoutManager.spanCount = 2
                productListAdapter.notifyDataSetChanged()
            }
        }

        productListAdapter.productEventListener = this

        toolbarView.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
        sortBtn.setOnClickListener{
            val dialog = MaterialAlertDialogBuilder(this,0)
                .setSingleChoiceItems(R.array.sort,productListViewModel.sort
                ) { dialog, selectedSortIndex ->
                    productListViewModel.onSelectedSortChangeByUser(selectedSortIndex)
                    dialog.dismiss()
                }.setTitle(R.string.sort)
            dialog.show()
        }

        productListViewModel.progressbarLiveData.observe(this){
        setProgressIndicator(it)
        }

        productListViewModel.selectedSortTitleLiveData.observe(this){
            selectedSortText.text = getString(it)
        }

        productListViewModel.productLiveData.observe(this){
            productListAdapter.products = it as ArrayList<Product>
        }
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(this,ProductDetailsActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }

    override fun onFavoriteBtnClick(product: Product) {
        productListViewModel.addProductToFavorite(product)
    }
}