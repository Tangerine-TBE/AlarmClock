package com.example.module_weather.domain

import org.litepal.crud.LitePalSupport

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.domain
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/18 11:44:57
 * @class describe
 */
data class WeatherCacheInfo(var city:String?,var long:String?,var lat:String?,var weatherMsg:String?):LitePalSupport()
