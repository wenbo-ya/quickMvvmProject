package com.rk.quickmvvmproject.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainVMRepository(val context: Context,val repository: MainRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  MainViewModel(context, repository) as T
    }

}