package com.twx.module_weather.domain

import com.twx.module_weather.utils.RequestState

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.domain
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/19 15:35:10
 * @class describe
 */
data class ValueWeatherData(val state: RequestState, val msg: ZipWeatherBean)
