package com.example.alarmclock.base


import android.graphics.Typeface
import com.example.alarmclock.service.TellTimeService
import com.example.alarmclock.util.Constants
import com.example.module_ad.advertisement.TTAdManagerHolder
import com.example.module_base.base.BaseApplication



/**
 * @name td_horoscope
 * @class name：com.example.td_horoscope.base
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/10 9:42
 * @class describe
 */
class MainBaseApplication:BaseApplication() {
     lateinit var mTypeface: Typeface
    override fun initChild() {
       TTAdManagerHolder.init(applicationContext)
        TellTimeService.startTellTimeService(this){ putExtra(Constants.TELL_TIME_SERVICE,3) }



    }
}