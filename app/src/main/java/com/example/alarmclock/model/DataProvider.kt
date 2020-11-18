package com.example.alarmclock.model

import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.model
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/16 16:52
 * @class describe
 */
object DataProvider {


     val bottomData= arrayListOf(
        ItemBean("设置", R.mipmap.ic_launcher),
        ItemBean("皮肤", R.mipmap.ic_launcher),
        ItemBean("闹钟", R.mipmap.ic_launcher),
        ItemBean("整点报时", R.mipmap.ic_launcher),
        ItemBean("更多", R.mipmap.ic_launcher)
    )

    val toolData= arrayListOf(
        ItemBean(title = "尺子",    hint = "Ruler"),
        ItemBean(title = "分贝仪",    hint = "Decibel meter" ),
        ItemBean(title = "量角器",     hint = "Protractor"),
        ItemBean(title = "水平仪", hint = "Level"),
        ItemBean(title = "高清镜子", hint = "mirror"),
        ItemBean(title = "手电筒", hint = "Flashlight"),
        ItemBean(title = "指南针", hint = "Compass"),
        ItemBean(title = "插画校对", hint = "Illustration"),
    )


}
