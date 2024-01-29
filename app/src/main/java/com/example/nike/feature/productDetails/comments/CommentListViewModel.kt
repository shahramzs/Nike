package com.example.nike.feature.productDetails.comments

import androidx.lifecycle.MutableLiveData
import com.example.nike.base.NikeSingleObserver
import com.example.nike.base.NikeViewModel
import com.example.nike.base.asyncNetworkRequest
import com.example.nike.data.Comment
import com.example.nike.data.repo.commentRepository.CommentRepository

class CommentListViewModel(code:String, commentRepository: CommentRepository):NikeViewModel() {
    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        progressbarLiveData.value = true
        commentRepository.getAll(code)
            .asyncNetworkRequest()
            .doFinally { progressbarLiveData.value = false }
            .subscribe(object: NikeSingleObserver<List<Comment>>(compositeDisposable){
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }

            })
    }
}