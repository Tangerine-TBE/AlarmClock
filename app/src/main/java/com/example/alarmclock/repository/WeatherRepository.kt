package com.example.alarmclock.repository

import com.example.alarmclock.bean.MjAqiBean
import com.example.alarmclock.bean.MjRealWeatherBean
import com.example.alarmclock.bean.ZipWeatherBean
import com.example.alarmclock.util.RetrofitManager
import com.example.alarmclock.view.IWeatherCallback
import com.example.module_base.base.BaseApplication
import com.example.module_base.base.BasePresent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.present.impl
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/25 14:07
 * @class describe
 */
object  WeatherRepository:BasePresent<IWeatherCallback>() {

    fun makeData(log:String, lat:String, function: BiFunction<MjRealWeatherBean,MjAqiBean,ZipWeatherBean>): Observable<ZipWeatherBean>{
         return RetrofitManager.createWeather<ServiceApi>().run {
            Observable.zip(getMjRealWeather(log,lat).subscribeOn(Schedulers.io()),getMjAqiWeather(log,lat).subscribeOn(Schedulers.io()),function)
        }
    }






}