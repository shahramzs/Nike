package com.example.nike.feature.productDetails.comments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nike.R
import com.example.nike.base.EXTRA_KEY_ID
import com.example.nike.base.NikeActivity
import com.example.nike.customView.NikeToolbar
import com.example.nike.data.Comment
import com.example.nike.feature.productDetails.CommentAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentListActivity : NikeActivity() {
    private val viewModelComment : CommentListViewModel by viewModel{ parametersOf(intent.extras!!.getString(
        EXTRA_KEY_ID)) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        val toolbar:NikeToolbar = findViewById(R.id.commentListToolbar)
        val commentsRv:RecyclerView = findViewById(R.id.commentsRv)
        val commentAdapter = CommentAdapter(true)

        viewModelComment.progressbarLiveData.observe(this){
            setProgressIndicator(it)
        }
        viewModelComment.commentsLiveData.observe(this){
            commentsRv.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
            commentAdapter.comments = it as ArrayList<Comment>
            commentsRv.adapter = commentAdapter
        }

        toolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
    }
}