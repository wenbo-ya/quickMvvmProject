package com.rk.common.network

import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 *
 *@Author 王--
 *创建时间 2021/4/10
 */
object ManagerDispose {
    private val  mDisposable = CompositeDisposable()
    fun addDisposable(disposable: Disposable) {
        Log.i("disposable","add")
        mDisposable.add(disposable)

    }


    fun clearDisposable() {
        mDisposable.clear()
        Log.i("disposable","clear--${mDisposable.size()}")
    }
}