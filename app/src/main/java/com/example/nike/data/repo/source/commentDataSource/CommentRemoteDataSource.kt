package com.example.nike.data.repo.source.commentDataSource

import com.example.nike.data.Comment
import com.example.nike.services.http.ApiService
import io.reactivex.Single

class CommentRemoteDataSource(private val apiService: ApiService):CommentDataSource {
    override fun getAll(code:String): Single<List<Comment>> = apiService.getComment(code)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}