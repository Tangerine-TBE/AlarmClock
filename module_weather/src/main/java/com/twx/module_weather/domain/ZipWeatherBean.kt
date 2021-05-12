package com.twx.module_weather.domain

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.bean
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/25 14:03
 * @class describe
 */
data class ZipWeatherBean(var realWeatherBean: MjRealWeatherBean,
                          var aqiBean: MjAqiBean,
                          var day15Msg:Mj15DayWeatherBean?=null,
                          var day24Msg:Mj24WeatherBean?=null,
                          var lifeBean:MutableList<MjLifeBean>?=null,
                          var aqi5Bean: Mj5AqiBean?=null,
                          ) {
}