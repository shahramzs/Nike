package com.example.nike.feature.productDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.nike.R
import com.example.nike.data.Comment
import java.text.SimpleDateFormat

class CommentAdapter(val showAll: Boolean = false): RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var comments = ArrayList<Comment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentTitle:TextView = itemView.findViewById(R.id.commentTitleTv)
        val commentName:TextView = itemView.findViewById(R.id.commentNameTv)
        val commentDate:TextView = itemView.findViewById(R.id.commentDateTv)
        val commentContent:TextView = itemView.findViewById(R.id.commentContent)

        fun bindComment(comment: Comment){
            val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd MMMM yyyy HH:mm:ss")
            val formattedDate = formatter.format(parser.parse(comment.time))

            commentTitle.text = comment.title
            commentName.text = comment.name
            commentDate.text = formattedDate
            commentContent.text = comment.comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment,parent,false))
    }

    override fun getItemCount(): Int {
        return if(comments.size > 3 && !showAll) 3 else (comments.size)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindComment(comments[position])
    }
}