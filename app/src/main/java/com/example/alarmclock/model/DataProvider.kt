package com.example.alarmclock.model

import android.Manifest
import com.example.alarmclock.R
import com.example.alarmclock.bean.IconTitleBean
import com.example.alarmclock.bean.ItemBean
import java.security.Permission
import java.security.Permissions

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
        ItemBean("设置", R.mipmap.icon_home_setting),
        ItemBean("皮肤", R.mipmap.icon_home_skin),
        ItemBean("闹钟", R.mipmap.icon_home_clock),
        ItemBean("整点报时", R.mipmap.icon_home_timing),
        ItemBean("更多", R.mipmap.icon_home_more)
    )

    val toolData = arrayListOf(
        ItemBean(icon = R.mipmap.icon_tool_chizi, title = "尺子", hint = "Ruler"),
        ItemBean(icon = R.mipmap.icon_tool_fenbei, title = "分贝仪", hint = "Decibel meter"),
        ItemBean(icon = R.mipmap.icon_tool_liangjiao, title = "量角器", hint = "Protractor"),
        ItemBean(icon = R.mipmap.icon_tool_shuiping, title = "水平仪", hint = "Level"),
        ItemBean(icon = R.mipmap.icon_tool_jingzi, title = "高清镜子", hint = "mirror"),
        ItemBean(icon = R.mipmap.icon_tool_diantong, title = "手电筒", hint = "Flashlight"),
        ItemBean(icon = R.mipmap.icon_tool_zhinan, title = "指南针", hint = "Compass"),
        ItemBean(icon = R.mipmap.icon_tool_jiaodui, title = "插画校对", hint = "Illustration"),
    )

    val setData = arrayListOf(
        ItemBean(title = "24小时制", isOpen = true),
        ItemBean(title = "强制屏幕横向展示", isOpen = false),
        ItemBean(title = "显示秒", isOpen = true),
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
        ItemBean(title = "隐私政策"),
        ItemBean(title = "账号注销"),
        ItemBean(title = "壁纸设置")
    )


    var skinData = arrayListOf(
        ItemBean(icon = R.mipmap.icon_skin_one,isOpen =false),
        ItemBean(icon = R.mipmap.icon_skin_two,isOpen =true),
        ItemBean(icon = R.mipmap.icon_skin_three,isOpen =false),
        ItemBean(icon = R.mipmap.icon_skin_four,isOpen =true),
        ItemBean(icon = R.mipmap.icon_skin_five,isOpen =false),
        ItemBean(icon = R.mipmap.icon_skin_six,isOpen =true),
        ItemBean(icon = R.mipmap.icon_skin_seven,isOpen =false),
        ItemBean(icon = R.mipmap.icon_skin_eight,isOpen =true),
        ItemBean(icon = R.mipmap.icon_skin_wace_one,isOpen =false),
        ItemBean(icon = R.mipmap.icon_skin_wace_two,isOpen =true)
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

    val calendarPermission = arrayListOf(
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR
    )

    val locationPermission = arrayListOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val clockPermission = arrayListOf(
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )


    val permissionList = arrayListOf(
        IconTitleBean(R.mipmap.icon_per_store, "用于数据缓存，提升响应速度", "存储"),
        IconTitleBean(R.mipmap.icon_per_location, "用户获取最新的天气信息", "定位"),
        IconTitleBean(R.mipmap.icon_per_calendar, "用于将闹钟时间添加到日历提醒", "日历")
    )


    val permissions = arrayListOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )


}
