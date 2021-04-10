package com.rk.common.base

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class BaseLifeObserver(val lifecycle: Lifecycle) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun activityStart() {
      //  Log.e("wwb", "start---${lifecycle.currentState}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun activityOnCreate() {
       // Log.e("wwb", "create---${lifecycle.currentState}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun activityOnResume() {
      //  Log.e("wwb", "resume---${lifecycle.currentState}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun activityOnPause() {
       // Log.e("wwb", "pause---${lifecycle.currentState}")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun activityOnStop() {
      //  Log.e("wwb", "stop---${lifecycle.currentState}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun activityOnDestroy() {
     //   Log.e("wwb", "destroy---${lifecycle.currentState}")
    }

}