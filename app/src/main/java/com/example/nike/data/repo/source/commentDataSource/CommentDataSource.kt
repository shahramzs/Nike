package com.example.nike.data.repo.source.commentDataSource

import com.example.nike.data.Comment
import io.reactivex.Single

interface CommentDataSource {
    fun getAll(code:String): Single<List<Comment>>

    fun insert(): Single<Comment>
}