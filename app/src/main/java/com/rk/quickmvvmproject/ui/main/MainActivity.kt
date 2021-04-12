package com.rk.quickmvvmproject.ui.main



import androidx.lifecycle.ViewModelProvider
import com.rk.common.base.BaseMVVMActivity
import com.rk.quickmvvmproject.R
import com.rk.quickmvvmproject.databinding.ActivityMainBinding


class MainActivity : BaseMVVMActivity<ActivityMainBinding, MainViewModel>() {
    override fun setLayout(): Int = R.layout.activity_main

    override fun initViewModel(): MainViewModel =
        ViewModelProvider(
            this,
            MainVMRepository( MainRepository())
        ).get(MainViewModel::class.java)

    override fun initView() {
        bindView.vm = vm


    }
}