package com.example.nike.customView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.nike.R

class NikeToolbar(context: Context,attributeSet: AttributeSet?): FrameLayout(context,attributeSet) {

    var onBackButtonClickListener : View.OnClickListener?=null
        set(value) {
            field = value
            val toolbarBackBtn: ImageView = findViewById(R.id.toolbarBackBtn)
            toolbarBackBtn.setOnClickListener(onBackButtonClickListener)
        }
    init {
        inflate(context, R.layout.view_toolbar,this)

        if(attributeSet!=null){
           val a = context.obtainStyledAttributes(attributeSet,R.styleable.NikeToolbar)
            val title = a.getString(R.styleable.NikeToolbar_nikeTitle)
            if(!title.isNullOrEmpty()){
                val toolbarTitleTv: TextView = findViewById(R.id.toolbarTitleTv)
                toolbarTitleTv.text = title
            }
            a.recycle()
        }
    }
}