package com.rk.common.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.rk.common.databinding.ActivityBaseBinding
import com.rk.common.network.ManagerDispose
import com.rk.common.utils.StatusBarUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseMVVMActivity<VDB : ViewDataBinding, VM : ViewModel> : BaseActivity() {

    lateinit var bindView: VDB
    lateinit var vm: VM
    lateinit var mContext: Context
    private lateinit var mBaseBinding: ActivityBaseBinding

    //  lateinit var baseObserver: BaseObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //隐藏标题栏
        //  requestWindowFeature(Window.FEATURE_NO_TITLE)
        bindView = DataBindingUtil.setContentView(this, setLayout())
        mContext = this
        vm = initViewModel()
        bindView.lifecycleOwner = this

//
        lifecycle.addObserver(BaseLifeObserver(lifecycle))
        initView()
//设置标题栏背景颜色
        //  StatusBarUtil.setColor(this, Color.WHITE)
        StatusBarUtil.setDarkMode(this)
    }


    abstract fun setLayout(): Int

    abstract fun initViewModel(): VM

    abstract fun initView()

    /**
     * 兼容状态栏透明（沉浸式）
     *
     * @param activity
     */
    open fun setImmersionStateMode(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT != Build.VERSION_CODES.LOLLIPOP) {
            // 透明状态栏
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 透明导航栏
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            )
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("disposable","destroy")
        ManagerDispose.clearDisposable()
    }
}