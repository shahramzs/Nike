package com.example.nike.base

import androidx.annotation.RequiresApi
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

abstract class NikeCompletableObserver(private val compositeDisposable: CompositeDisposable):CompletableObserver {
    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }


    @RequiresApi(34)
    override fun onError(e: Throwable) {
        EventBus.getDefault().post(NikeExceptionMapper.map(e))
        Timber.tag("nikeCompletableObserver").e(e)
    }
}