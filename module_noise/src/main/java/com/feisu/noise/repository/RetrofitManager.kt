package com.feisu.noise.repository


import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
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

    const val URL="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/"

    const val PICTURE="https://xiaotupian.oss-cn-beijing.aliyuncs.com/baizaoyin/00.png"


    private val url:Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(geClient())
        .build()


      fun  createNormal()= url.create(ServiceApi::class.java)

     private fun geClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(600, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(600, TimeUnit.SECONDS)
        //打印网络请求日志
        val httpLoggingInterceptor = LoggingInterceptor.Builder()
            .loggable(false)
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request("请求")
            .response("响应")
            .build()
         return     httpClientBuilder.addInterceptor(httpLoggingInterceptor).build()

    }
}