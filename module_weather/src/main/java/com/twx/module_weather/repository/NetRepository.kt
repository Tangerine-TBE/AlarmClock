package com.twx.module_weather.repository

import com.twx.module_weather.domain.*
import com.twx.module_weather.utils.Constants
import com.twx.module_weather.utils.RetrofitManager
import io.reactivex.Observable
import io.reactivex.functions.Function6
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.present.impl
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/25 14:07
 * @class describe
 */
object  NetRepository {


    fun weatherData(
            log: String,
            lat: String,
            function: Function6<MjRealWeatherBean, MjAqiBean, Mj15DayWeatherBean, Mj24WeatherBean, Mj5AqiBean, ResponseBody, ZipWeatherBean>
    ): Observable<ZipWeatherBean>{
        return RetrofitManager.createWeather<ServiceApi>().run {
            Observable.zip(
                    getMjRealWeather(log, lat).subscribeOn(Schedulers.io()),
                    getMjAqiWeather(log, lat).subscribeOn(Schedulers.io()),
                    getMj15DayWeather(log, lat).subscribeOn(Schedulers.io()),
                    getMj24HoursWeather(log, lat).subscribeOn(Schedulers.io()),
                    getMj5AqiWeather(log, lat).subscribeOn(Schedulers.io()),
                    getMjLifeWeather(log, lat).subscribeOn(Schedulers.io()),
                    function
            )
        }
    }


   suspend fun addressData(city: String)= RetrofitManager.createAddress<ServiceApi>().getAddress(String.format(Constants.ADDRESS, city))


    suspend fun huangLiData(year:String,month:String,day:String):HuangLiBean{
        return RetrofitManager.createHuangLi<ServiceApi>().getHuangLi(Constants.HUANG_LI_KEY,day,month,year)
    }



}