package com.rk.quickmvvmproject.ui.main


import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.rk.common.base.BaseMVVMActivity

import com.rk.quickmvvmproject.R
import com.rk.quickmvvmproject.databinding.ActivityMainBinding
import com.rk.quickmvvmproject.utils.getA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : BaseMVVMActivity<ActivityMainBinding, MainViewModel>() {
    override fun setLayout(): Int = R.layout.activity_main

    override fun initViewModel(): MainViewModel =
        ViewModelProvider(
            this,
            MainVMRepository( MainRepository())
        ).get(MainViewModel::class.java)

    override fun initView() {
        bindView.vm = vm
//        getSharedPreferences().edit()
     GlobalScope.launch {
         getA()
     }
    }



}