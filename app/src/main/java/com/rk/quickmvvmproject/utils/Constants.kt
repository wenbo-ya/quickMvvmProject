package com.rk.quickmvvmproject.utils

import android.content.Context
import com.rk.quickmvvmproject.MyApplication
import com.rk.quickmvvmproject.http.ApiService
import com.rk.quickmvvmproject.http.RetrofitManager


class Constants {

    companion object {
        val userApi = RetrofitManager.instance.create<ApiService>()
//            .instance.createService(ApiService::class.java)
//
//        val wyMusicApi=RetrofitManager.instance.createMusic<ApiService>()

        val mSharedPreferences =
            MyApplication.mApplicationContext.getSharedPreferences("data", Context.MODE_PRIVATE)

    }


}




object ShareUtil {
    const val TOKEN = "TOKEN"

    const val USERPHONE="USERPHONE"
}
