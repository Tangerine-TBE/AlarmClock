package com.twx.clock.view

import com.twx.module_weather.domain.ZipWeatherBean

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.view
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/25 14:04
 * @class describe
 */
interface IWeatherCallback {

   fun  onLoadWeather(data: ZipWeatherBean)

    fun  onLoadError(str:String)
}