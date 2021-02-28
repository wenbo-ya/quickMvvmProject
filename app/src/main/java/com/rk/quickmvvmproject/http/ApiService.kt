package com.rk.quickmvvmproject.http


import com.rk.quickmvvmproject.model.LoginModel
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {


    object COMMON {
        const val LOGIN = "api-server/"
    }

    @POST(COMMON.LOGIN)
    fun  toLogin():Observable<LoginModel>


}