package com.rk.quickmvvmproject.ui.main

import com.rk.quickmvvmproject.model.BannerModel
import com.rk.quickmvvmproject.model.LoginModel
import com.rk.quickmvvmproject.utils.Constants
import com.rk.quickmvvmproject.utils.simplifySubscribe
import io.reactivex.Observable

import okhttp3.RequestBody

class MainRepository  {

    fun toLogin(  requestBody: RequestBody): Observable<LoginModel> {
        return  Constants.userApi.toLogin().simplifySubscribe()
    }


    fun getBanner():Observable<BannerModel>{
        return  Constants.userApi.getBanner().simplifySubscribe()
    }
}