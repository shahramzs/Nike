package com.example.nike.data.repo.commentRepository

import com.example.nike.data.Comment
import io.reactivex.Single

interface CommentRepository {
    fun getAll(code:String):Single<List<Comment>>

    fun insert():Single<Comment>
}