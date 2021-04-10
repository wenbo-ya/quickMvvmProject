package com.rk.quickmvvmproject.ui.main

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.rk.common.base.BaseActivity
import com.rk.common.base.BaseMVVMActivity
import com.rk.common.network.BaseObserver
import com.rk.quickmvvmproject.MyApplication
import com.rk.quickmvvmproject.R
import com.rk.quickmvvmproject.model.BannerModel
import com.rk.quickmvvmproject.model.LoginModel
import com.rk.quickmvvmproject.utils.*
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.delay
import org.json.JSONObject

class MainViewModel(private val repository: MainRepository) : ViewModel() {


    val viewClick = View.OnClickListener {
        it.isClickable = false

        clickDelay({
            delay(1000L)
            it.isClickable = true
        }, {

        })
        when (it.id) {
            R.id.tv_login -> {
                getBanner()
            }
            R.id.iv_imageView -> {
             //   toast("aaa")
            }


        }
    }

    private fun getBanner() {
        repository.getBanner()
            .subscribe(object : BaseObserver<BannerModel>() {
                override fun onSuccess(t: BannerModel) {
                    toast(t.errorCode.toString())
                }

                override fun onBaseError(e: Throwable) {
                }
            })
    }


}