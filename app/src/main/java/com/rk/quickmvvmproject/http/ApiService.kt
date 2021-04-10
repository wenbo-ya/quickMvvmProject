package com.rk.quickmvvmproject.http


import com.rk.quickmvvmproject.model.BannerModel
import com.rk.quickmvvmproject.model.LoginModel
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {


    object COMMON {
        const val LOGIN = "api-server/"
        const val  BANNER ="banner/json"
    }

    @POST(COMMON.LOGIN)
    fun  toLogin():Observable<LoginModel>


    @GET(COMMON.BANNER)
    fun getBanner():Observable<BannerModel>
}