package com.feisu.noise.repository

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url


/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.model
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/25 13:59
 * @class describe
 */
interface ServiceApi {

    @Streaming
    @GET
     suspend fun downFile(@Url url: String?): ResponseBody


}