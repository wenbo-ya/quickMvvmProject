package com.rk.quickmvvmproject.utils

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.rk.quickmvvmproject.R
import kotlinx.coroutines.delay

/**
 *
 *@Author 王--
 *创建时间 2021/4/12
 */
@BindingAdapter(value = ["imageUrl"])
fun loadImage(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url)
        .placeholder(R.drawable.ic_launcher_background)
        .into(imageView)
}

@BindingAdapter(value = ["bindClick","bindingData"])
fun bindingClick(view: View, s : String,code: Int) {
    view.setOnClickListener {
        view.isClickable = false
       clickDelay(
            {
                delay(1000L)
                view.isClickable = true
            }, {
            }
        )
        when(view.id){
            R.id.tv_login->{
                toast("$s---$code")
            }
            R.id.tv_test->{
                toast("$s---$code")
            }
        }


    }
}
