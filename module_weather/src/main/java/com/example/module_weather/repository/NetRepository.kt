package com.example.module_weather.repository

import com.example.module_weather.domain.*
import com.example.module_weather.utils.Constants
import com.example.module_weather.utils.RetrofitManager
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function6
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.util.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.present.impl
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/25 14:07
 * @class describe
 */
object  NetRepository {

    fun makeData(
            log: String,
            lat: String,
            function: BiFunction<MjRealWeatherBean, MjAqiBean, ZipWeatherBean>
    ): Observable<ZipWeatherBean>{
         return RetrofitManager.createWeather<ServiceApi>().run {
            Observable.zip(
                    getMjRealWeather(log, lat).subscribeOn(Schedulers.io()), getMjAqiWeather(
                    log,
                    lat
            ).subscribeOn(Schedulers.io()), function
            )
        }
    }

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


    suspend fun huangLiData():HuangLiBean{
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DAY_OF_MONTH]
        return RetrofitManager.createHuangLi<ServiceApi>().getHuangLi(Constants.HUANG_LI_KEY,day.toString(),month.toString(),year.toString())
    }

}