package com.rk.common.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.rk.common.databinding.FragmentBaseBinding

abstract class BaseLazyMVVMFragment<VDB : ViewDataBinding, VM : ViewModel> : BaseFragment() {
    lateinit var vm: VM
    lateinit var bindView: VDB
    private lateinit var mBaseBinding: FragmentBaseBinding

    lateinit var mContext: Context
    private lateinit var mContainer: FrameLayout

    private var isLazyLoaded = false//懒加载过
    private var isPrepared = false
    private var isFirst = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(setContent(), container, false)
        bindView = DataBindingUtil.bind(inflate)!!
        mContext = this.context!!
        bindView.lifecycleOwner = this
        vm = initViewModel()
        lifecycle.addObserver(BaseLifeObserver(lifecycle))

        return inflate
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isPrepared = true
    }

    override fun onResume() {
        super.onResume()
        if (userVisibleHint) {
            userVisibleHint = true
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoad()
        }

    }


    abstract fun setContent(): Int


    abstract fun initViewModel(): VM

    abstract fun initView()

    abstract fun isNotFirst()

    protected open fun lazyLoad() {
        if (userVisibleHint) {
            if (!isFirst) {
                isNotFirst()
            } else {
                if (isPrepared && userVisibleHint && !isLazyLoaded) {
                    Log.d("BaseLazy", javaClass.name + "->initData()")
                    initView()
                    isLazyLoaded = true
                    isFirst = false
                }
            }

        }


    }

    //do something
    protected open fun onInvisible() {}

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

}