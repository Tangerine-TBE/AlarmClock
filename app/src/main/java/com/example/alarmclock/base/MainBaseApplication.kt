package com.example.alarmclock.base


import com.example.alarmclock.R
import com.example.module_base.base.BaseApplication
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


/**
 * @name td_horoscope
 * @class name：com.example.td_horoscope.base
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/10 9:42
 * @class describe
 */
class MainBaseApplication:BaseApplication() {



    override fun initChild() {
       // TTAdManagerHolder.init(this)
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .build())
    }
}