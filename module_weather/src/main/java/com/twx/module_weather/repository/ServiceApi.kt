package com.twx.module_weather.repository


import com.twx.module_weather.domain.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.model
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/25 13:59
 * @class describe
 */
interface ServiceApi {
    //坐标转换
    @GET
    suspend fun getAddress(@Url city: String): AddressBean

    //黄历
    @GET("huangli/date")
    suspend fun getHuangLi(@Header("Authorization") key: String, @Query("day") day: String, @Query("month") month: String, @Query("year") year: String): HuangLiBean


    //墨迹天气------------------------------------------------------------
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


    //24小时天气
    @POST("forecast24hours")
    fun getMj24HoursWeather(
            @Query("lon") lon: String?,
            @Query("lat") lat: String?
    ): Observable<Mj24WeatherBean>

    //15天天气
    @POST("forecast15days")
    fun getMj15DayWeather(
            @Query("lon") lon: String,
            @Query("lat") lat: String
    ): Observable<Mj15DayWeatherBean>

    //生活指数
    @POST("index")
    fun getMjLifeWeather(
            @Query("lon") lon: String,
            @Query("lat") lat: String
    ): Observable<ResponseBody>


    @POST("aqiforecast5days")
    fun getMj5AqiWeather(
            @Query("lon") lon: String,
            @Query("lat") lat: String
    ): Observable<Mj5AqiBean>



}