package com.feisukj.base_library.utils

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.feisukj.base_library.ActivityEntrance
import com.feisukj.base_library.ad.ADConstants
import com.feisukj.base_library.ad.ADConstants.AD_APP_BACKGROUND_TIME

class ActivityLifecycleCallbacksImpl private constructor():Application.ActivityLifecycleCallbacks {
    companion object{
        val instance by lazy { ActivityLifecycleCallbacksImpl() }
        var isFront=false
            private set
        private var resumedActivity=0

        var isNoShowAd=false
    }
    private val frontBackgroundCallback by lazy { HashSet<FrontBackgroundCallback>() }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        RunningActivitys.addActivity(activity)
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
        resumedActivity++
        if (resumedActivity >0 && !isFront){//进入前台
            isFront =true
            frontBackgroundCallback.forEach {
                it.frontCallback(activity)
            }
            if (activity.javaClass!=ActivityEntrance.SplashActivity.cls&&activity.javaClass!=ActivityEntrance.SplashActivityAD.cls){
                gotoSplashADActivity(activity)
            }
            Log.i("前后台","进入前台")
        }
        RunningActivitys.setCurrentActivity(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        resumedActivity--
        if (resumedActivity ==0&&isFront){//进入后台
            isFront =false
            SPUtil.instance.putLong(AD_APP_BACKGROUND_TIME, System.currentTimeMillis())
            frontBackgroundCallback.forEach {
                it.backgroundCallback(activity)
            }
            Log.i("前后台","进入后台")
        }
    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        RunningActivitys.removeActivity(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    private fun gotoSplashADActivity(activity: Activity) {
        if (isNoShowAd){
            isNoShowAd=false
            return
        }
        if (SPUtil.instance.getBoolean(ADConstants.AD_SPLASH_STATUS) && needSplashAD()) {
            val cls = ActivityEntrance.SplashActivityAD.cls
            if (cls != null)
                activity.startActivity(Intent(activity, cls))
        }
    }
    private fun needSplashAD(): Boolean {
        val current = System.currentTimeMillis()
        val background = SPUtil.instance.getLong(AD_APP_BACKGROUND_TIME, 0)
        val gapTime = (current - background) / 1000
        val serverTime = SPUtil.instance.getLong(ADConstants.AD_SPREAD_PERIOD, 5)
        return gapTime >= serverTime && serverTime != 0L
    }


    fun addFrontBackgroundCallback(callback: FrontBackgroundCallback){
        frontBackgroundCallback.add(callback)
    }

    fun removeFrontBackgroundCallback(callback: FrontBackgroundCallback){
        frontBackgroundCallback.remove(callback)
    }

    interface FrontBackgroundCallback{
        fun frontCallback(activity: Activity)
        fun backgroundCallback(activity: Activity)
    }
}