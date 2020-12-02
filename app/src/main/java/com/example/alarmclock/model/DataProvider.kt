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


    val bottomData = arrayListOf(
            ItemBean("设置", R.mipmap.ic_launcher),
            ItemBean("皮肤", R.mipmap.ic_launcher),
            ItemBean("闹钟", R.mipmap.ic_launcher),
            ItemBean("整点报时", R.mipmap.ic_launcher),
            ItemBean("更多", R.mipmap.ic_launcher)
    )

    val toolData = arrayListOf(
            ItemBean(title = "尺子", hint = "Ruler"),
            ItemBean(title = "分贝仪", hint = "Decibel meter"),
            ItemBean(title = "量角器", hint = "Protractor"),
            ItemBean(title = "水平仪", hint = "Level"),
            ItemBean(title = "高清镜子", hint = "mirror"),
            ItemBean(title = "手电筒", hint = "Flashlight"),
            ItemBean(title = "指南针", hint = "Compass"),
            ItemBean(title = "插画校对", hint = "Illustration"),
    )

    val setData = arrayListOf(
            ItemBean(title = "24小时制", isOpen = false),
            ItemBean(title = "强制屏幕横向展示", isOpen = false),
            ItemBean(title = "显示秒", isOpen = false),
            ItemBean(title = "锁屏时间展示", isOpen = false)
    )

    var setClockData = arrayListOf(
            ItemBean(title = "重复", hint = "仅一次"),
            ItemBean(title = "响铃时震动", isOpen = true),
            ItemBean(title = "响铃后删除此闹钟", isOpen = false)

    )

    val setOtherData = arrayListOf(
            ItemBean(title = "问题和建议"),
            ItemBean(title = "权限授予"),
            ItemBean(title = "用户协议"),
            ItemBean(title = "隐私政策")
    )


    val viewData = arrayListOf(
            ItemBean(title = "1"),
            ItemBean(title = "2"),
            ItemBean(title = "3"),
            ItemBean(title = "4")
    )

    val repeatData = arrayListOf(
            ItemBean(title = "仅一次", isOpen = false),
            ItemBean(title = "周一至周五", isOpen = false),
            ItemBean(title = "每天", isOpen = false),
            ItemBean(title = "自定义", isOpen = false)
    )

    val weekList = arrayListOf("周一", "周二", "周三", "周四", "周五")
    val diyWeekList = arrayListOf(
            ItemBean(title = "一", icon = 1, hint = "周一", week = 2),
            ItemBean(title = "二", icon = 2, hint = "周二", week = 3),
            ItemBean(title = "三", icon = 3, hint = "周三", week = 4),
            ItemBean(title = "四", icon = 4, hint = "周四", week = 5),
            ItemBean(title = "五", icon = 5, hint = "周五", week = 6),
            ItemBean(title = "六", icon = 6, hint = "周六", week = 7),
            ItemBean(title = "日", icon = 7, hint = "周日", week = 1)
    )
}
