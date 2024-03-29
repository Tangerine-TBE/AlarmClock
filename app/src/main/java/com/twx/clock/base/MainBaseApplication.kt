package com.twx.clock.base

import android.app.Activity
import android.os.Bundle
import com.twx.clock.service.TellTimeService
import com.twx.clock.util.Constants
import com.twx.module_ad.advertisement.TTAdManagerHolder
import com.twx.module_ad.utils.BaseBackstage
import com.twx.module_base.base.BaseApplication
import com.twx.module_usercenter.utils.SpUtil
import com.llew.huawei.verifier.LoadedApkHuaWei


/**
 * @name td_horoscope
 * @class name：com.example.td_horoscope.base
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/10 9:42
 * @class describe
 */
class MainBaseApplication: BaseApplication() {
    override fun initChild() {
        LoadedApkHuaWei.hookHuaWeiVerifier(this)
       TTAdManagerHolder.init(applicationContext)
        TellTimeService.startTellTimeService(this){ putExtra(Constants.TELL_TIME_SERVICE, 3) }
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            private var activityStartCount = 0

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }

            override fun onActivityStarted(activity: Activity) {
                activityStartCount++;
                //数值从0 变到 1 说明是从后台切到前台
                if (activityStartCount == 1) {
                    //从后台切到前台
                    if (!SpUtil.isVIP()) {
                        BaseBackstage.setBackstage(this@MainBaseApplication)
                    }
                }
            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {
                activityStartCount--;
                //数值从1到0说明是从前台切到后台
                if (activityStartCount == 0) {
                    //从前台切到后台
                    if (!SpUtil.isVIP()) {
                        BaseBackstage.setStop(this@MainBaseApplication)
                    }
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })

    }
}