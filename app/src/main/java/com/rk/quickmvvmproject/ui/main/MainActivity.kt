package com.rk.quickmvvmproject.ui.main


import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.rk.common.base.BaseMVVMActivitys
import com.rk.quickmvvmproject.R
import com.rk.quickmvvmproject.databinding.ActivityMainBinding

class MainActivity : BaseMVVMActivitys<ActivityMainBinding, MainViewModel>() {
    override fun setLayout(): Int = R.layout.activity_main

    override fun initViewModel(): MainViewModel =
        ViewModelProvider(
            this,
            MainVMRepository(mContext, MainRepository())
        ).get(MainViewModel::class.java)

    override fun initView() {
        bindView.vm = vm
    }

}