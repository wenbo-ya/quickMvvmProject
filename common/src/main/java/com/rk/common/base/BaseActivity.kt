package com.rk.common.base

import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.rk.common.utils.ActivityCollector

open class BaseActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        ActivityCollector.addActivity(this)
        /*      StatusBarUtil.setColor(this, Color.WHITE)
              StatusBarUtil.setDarkMode(this)*/
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}