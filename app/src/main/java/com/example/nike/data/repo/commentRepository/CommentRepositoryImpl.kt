package com.example.nike.data.repo.commentRepository

import com.example.nike.data.Comment
import com.example.nike.data.repo.source.commentDataSource.CommentDataSource
import io.reactivex.Single

class CommentRepositoryImpl(private val commentDataSource: CommentDataSource):CommentRepository {
    override fun getAll(code:String): Single<List<Comment>> = commentDataSource.getAll(code)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}