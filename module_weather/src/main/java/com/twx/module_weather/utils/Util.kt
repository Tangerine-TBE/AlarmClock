package com.twx.module_weather.utils

import java.util.*

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.utils
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/18 13:58:36
 * @class describe
 */


//格式化城市
fun formatCity(city: String): String {
    return if (city.endsWith("市")) {
        city.replace("市", "")
    } else city
}


//格式化温度
fun addTemSymbol(tem: String): String {
    return "$tem°"
}


fun selectIcon(): Boolean {
    val cal = Calendar.getInstance() // 当前日期
    val hour = cal[Calendar.HOUR_OF_DAY] // 获取小时
    val minute = cal[Calendar.MINUTE] // 获取分钟
    val minuteOfDay = hour * 60 + minute // 从0:00分开是到目前为止的分钟数
    val start = 6 * 60 // 起始时间 8:30的分钟数
    val end = 18 * 60 // 结束时间 18:00的分钟数
    return minuteOfDay in start..end
}


enum class RequestState{
    NORMAL,REFRESH
}

enum class UserAction{
    NONE,ADD,DELETE
}