package com.example.module_weather.utils

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
