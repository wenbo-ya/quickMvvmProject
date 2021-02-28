package com.rk.quickmvvmproject.ui.main

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.rk.common.network.BaseObserver
import com.rk.quickmvvmproject.R
import com.rk.quickmvvmproject.model.LoginModel
import com.rk.quickmvvmproject.utils.*
import kotlinx.coroutines.delay
import org.json.JSONObject

class MainViewModel(val context: Context, val repository: MainRepository) : ViewModel() {


    val viewClick = View.OnClickListener {
        it.isClickable = false

        clickDelay({
            delay(1000L)
            it.isClickable = true
        }, {

        })
        when (it.id) {
            R.id.tv_login -> {
                toast("已点击")
                toLogin()
            }


        }
    }


    private fun toLogin() {

        repository.toLogin(getRequestBody(JSONObject().addParameter {
            put("phone", "1234567890")
            put("code", "1234")
        }.toString()))
            .subscribe(object : BaseObserver<LoginModel>() {
                override fun onSuccess(t: LoginModel) {

                    when (t.code) {
                        "200" -> {
                            //简化跳转  startActivityT<类名>(mContext)
                        }
                        else->{
                            toast(t.message)
                        }
                    }
                }

                override fun onBaseError(e: Throwable) {

                }

            })

    }

}