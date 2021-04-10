package com.rk.common.network

import android.app.Application
import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class BaseObserver<T> : Observer<T> {


    private var mProgressDialog: KProgressHUD? = null  //进度窗体

    constructor()
    constructor(context: Context) {
        showInfoProgressDialog(context, "加载中...")
    }

    override fun onNext(t: T) {
        onFinish()
        onSuccess(t)
    }

    override fun onComplete() {
        onFinish()
    }

    override fun onError(e: Throwable) {
        onFinish()
        onBaseError(e)
    }

    override fun onSubscribe(d: Disposable) {
        ManagerDispose.addDisposable(d)
    }

    abstract fun onSuccess(t: T)

    abstract fun onBaseError(e: Throwable)


    /**
     * 隐藏等待条
     */
    private fun hideInfoProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
            mProgressDialog = null
        }
    }

    /**
     * 显示等待条
     */
    private fun showInfoProgressDialog(
        context: Context,
        string: String?
    ) {
        if (mProgressDialog == null) {
            mProgressDialog = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            mProgressDialog!!.setCancellable(false)
        }
        mProgressDialog!!.setLabel(string)
        if (!mProgressDialog!!.isShowing) {
            mProgressDialog!!.show()
        }
    }

    /**
     * 当请求完成时调用（无论成功或失败）
     */
    private fun onFinish() {
        //如果没有加入进度条操作可以不调用super
        hideInfoProgressDialog()
    }

}