package com.rk.common.base

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.rk.common.R
import com.rk.common.databinding.ActivityBaseBinding

abstract class BaseMVVMToolBarActivity<VDB : ViewDataBinding, VM : ViewModel> : BaseActivity() {

    lateinit var bindView: VDB
    lateinit var vm: VM
    lateinit var mContext: Context
    private lateinit var mBaseBinding: ActivityBaseBinding

  //  lateinit var baseObserver: BaseObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //隐藏标题栏
        //  requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(setLayout())

    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        mBaseBinding =
            DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false)
        bindView = DataBindingUtil.inflate(layoutInflater, layoutResID, null, false)
        val params = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        bindView.root.layoutParams = params
        val mContainer = mBaseBinding.container
        mContainer.addView(bindView.root)
        window.setContentView(mBaseBinding.root)
        mContext = this
        vm = initViewModel()
        bindView.lifecycleOwner = this

//
       lifecycle.addObserver(BaseLifeObserver(lifecycle))
        initView()
    }


    abstract fun setLayout(): Int

    abstract fun initViewModel(): VM

    abstract fun initView()


    /**
     * 隐藏标题栏
     */
    protected open fun hideTitleBar() {
        mBaseBinding.commonTitle.rlTitleBar.visibility = View.GONE
    }

    open fun getTitleBackButton(): FrameLayout? {
        return mBaseBinding.commonTitle.llLiftBack
    }

    /**
     * 隐藏返回箭头
     */
    protected open fun hideBackImg() {
        mBaseBinding.commonTitle.llLiftBack.visibility = View.GONE
    }

    /**
     * 设置标题
     */
    protected open fun setTitle(title: String?) {
        mBaseBinding.commonTitle.tvTitle.text = if (!TextUtils.isEmpty(title)) title else ""
    }


    /**
     * 设置右侧文字
     */
    protected open fun setRightTitle(
        rightTitle: String?,
        listener: View.OnClickListener?
    ) {
        mBaseBinding.commonTitle.tvRightText.text =
            if (!TextUtils.isEmpty(rightTitle)) rightTitle else ""
        mBaseBinding.commonTitle.llRightText.visibility = View.VISIBLE
        mBaseBinding.commonTitle.llRightImg.visibility = View.GONE
        if (listener != null) {
            mBaseBinding.commonTitle.llRightText.setOnClickListener(listener)
        }
    }

    protected open fun getRightTitle(): String? {
        return mBaseBinding.commonTitle.tvRightText.text.toString()
    }

    protected open fun setRighttxtTitle(content: String?) {
        mBaseBinding.commonTitle.tvRightText.text = content
    }

    /**
     * 设置右侧图片
     */
    protected open fun setRightImg(
        img: Int,
        listener: View.OnClickListener?
    ) {
        mBaseBinding.commonTitle.llRightText.visibility = View.GONE
        mBaseBinding.commonTitle.llRightImg.visibility = View.VISIBLE
        if (img > 0) {
            mBaseBinding.commonTitle.ivRightImg.setImageResource(img)
        }
        if (listener != null) {
            mBaseBinding.commonTitle.llRightImg.setOnClickListener(listener)
        }
    }

    /**
     * 设置右侧图片
     */
//    protected open fun setRightImg(
//        img: Int,
//        listener: View.OnClickListener?,
//        width: Float,
//        height: Float
//    ) {
//        mBaseBinding.commonTitle.llRightText.visibility = View.GONE
//        mBaseBinding.commonTitle.llRightImg.visibility = View.VISIBLE
//        if (img > 0) {
//            mBaseBinding.commonTitle.ivRightImg.setImageResource(img)
//        }
//        if (listener != null) {
//            mBaseBinding.commonTitle.llRightImg.setOnClickListener(listener)
//        }
//        if (width > 0 && height > 0) {
//            val layoutParams =
//                mBaseBinding.commonTitle.ivRightImg.layoutParams
//            layoutParams.width = CommonUtils.dip2px(mContext, width)
//            layoutParams.height = CommonUtils.dip2px(mContext, height)
//            mBaseBinding.commonTitle.ivRightImg.layoutParams = layoutParams
//        }
//    }
}