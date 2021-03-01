package com.rk.quickmvvmproject

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.rk.common.utils.wheel.PickCityUtil
import com.rk.quickmvvmproject.http.RetrofitManager

class MyApplication:Application() {



    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mApplicationContext: Context
        const val  BASE_URL="https://www.baidu.com/"
    }
    override fun onCreate() {
        super.onCreate()
        //多功能选择(单选，多选，日期)
        PickCityUtil.initPickView(applicationContext)


        mApplicationContext = applicationContext
        MultiDex.install(applicationContext)

        RetrofitManager.instance.apply {

            init(BASE_URL)
        }
    }
}