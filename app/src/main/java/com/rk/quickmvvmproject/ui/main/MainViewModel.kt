package com.rk.quickmvvmproject.ui.main

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
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


    val login="登录"
    val testing="测试"

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


val ivUrl="https://namixinyan.oss-cn-hangzhou.aliyuncs.com/temp/20201110/163333249079.png"


}