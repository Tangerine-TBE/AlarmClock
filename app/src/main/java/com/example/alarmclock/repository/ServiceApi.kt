package com.example.alarmclock.repository

import com.example.alarmclock.bean.MjAqiBean
import com.example.alarmclock.bean.MjRealWeatherBean
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.model
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/25 13:59
 * @class describe
 */
interface ServiceApi {

    //实时天气
    @POST("condition")
    fun getMjRealWeather(
        @Query("lon") lon: String,
        @Query("lat") lat: String
    ): Observable<MjRealWeatherBean>

    //空气质量指数

    @POST("aqi")
    fun getMjAqiWeather(
        @Query("lon") lon: String,
        @Query("lat") lat: String
    ): Observable<MjAqiBean>



}