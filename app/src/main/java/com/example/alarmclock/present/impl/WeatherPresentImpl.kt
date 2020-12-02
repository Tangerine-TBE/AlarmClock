package com.example.alarmclock.present.impl

import com.example.alarmclock.bean.MjAqiBean
import com.example.alarmclock.bean.MjRealWeatherBean
import com.example.alarmclock.bean.ZipWeatherBean
import com.example.alarmclock.model.ServiceApi
import com.example.alarmclock.util.RetrofitManager
import com.example.alarmclock.view.IWeatherCallback
import com.example.module_base.base.BaseApplication
import com.example.module_base.base.BasePresent
import com.example.module_base.util.LogUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.present.impl
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/25 14:07
 * @class describe
 */
object  WeatherPresentImpl:BasePresent<IWeatherCallback>() {

    private fun makeData(log:String, lat:String, function: BiFunction<MjRealWeatherBean,MjAqiBean,ZipWeatherBean>): Observable<ZipWeatherBean>{
         return RetrofitManager.createWeather<ServiceApi>().run {
            Observable.zip(getMjRealWeather(log,lat).subscribeOn(Schedulers.io()),getMjAqiWeather(log,lat).subscribeOn(Schedulers.io()),function)
        }
    }


    fun getWeatherInfo(log:String,lat:String){
        makeData(log,lat, BiFunction { t1, t2 ->
            ZipWeatherBean(t1,t2)
        }).subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                BaseApplication.getMainHandler().post {
                    for (mCallback in mCallbacks) {
                        mCallback.onLoadWeather(it)
                    }
                }

            }, Consumer {
                BaseApplication.getMainHandler().post {
                    for (mCallback in mCallbacks) {
                        mCallback.onLoadError(it.message.toString())
                    }
                }
            })
    }




}