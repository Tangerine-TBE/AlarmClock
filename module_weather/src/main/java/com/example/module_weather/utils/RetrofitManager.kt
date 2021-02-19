package com.example.module_weather.utils


import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.util
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/25 13:47
 * @class describe
 */
object RetrofitManager {

     val mWeather:Retrofit = Retrofit.Builder()
             .baseUrl(Constants.WEATHER_URL)
             .addConverterFactory(GsonConverterFactory.create())
             .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getMjClient()?.build())
             .build()

    val mAddress:Retrofit = Retrofit.Builder()
        .baseUrl(Constants.ADDRESS_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val mHuangLi:Retrofit = Retrofit.Builder()
            .baseUrl(Constants.HUANG_LI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

     inline fun <reified T> createWeather(): T = mWeather.create(T::class.java)
     inline fun <reified T> createAddress(): T = mAddress.create(T::class.java)
     inline fun <reified T> createHuangLi(): T = mHuangLi.create(T::class.java)

     private fun getMjClient(): OkHttpClient.Builder? {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(10, TimeUnit.SECONDS)
        //打印网络请求日志
        val httpLoggingInterceptor = LoggingInterceptor.Builder()
            .loggable(false)
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .addHeader("Authorization", "APPCODE 52ce58f29858415596449874e5555eec")
            .request("请求")
            .response("响应")
            .build()
        httpClientBuilder.addInterceptor(httpLoggingInterceptor)
        return httpClientBuilder
    }
}