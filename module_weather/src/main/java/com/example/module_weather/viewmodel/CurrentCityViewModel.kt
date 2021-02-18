package com.example.module_weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.example.module_base.base.BaseViewModel
import com.example.module_base.util.DateUtil
import com.example.module_weather.db.DbHelper
import com.example.module_weather.domain.MjLifeBean
import com.example.module_weather.domain.WeatherCacheInfo
import com.example.module_weather.domain.ZipWeatherBean
import com.example.module_weather.repository.NetRepository
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.viewmodel
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/18 10:51:40
 * @class describe
 */
class CurrentCityViewModel:BaseViewModel() {


    val weatherInfo by lazy {
        MutableLiveData<ZipWeatherBean>()
    }


    fun getWeatherMsg(city:String,long: String, lat: String){
        NetRepository.weatherData(long, lat) { mjRealWeatherBean, mjAqiBean, mj15DayWeatherBean, mj24WeatherBean, mj5AqiBean, responseBody ->
            val string: String = responseBody.string()
            val jsonObject = JSONObject(string)
            val data = jsonObject.optJSONObject("data")
            val liveIndex = data.getJSONObject("liveIndex")
            val jsonArray = liveIndex.getJSONArray(DateUtil.getDate())
            val lifeBeans = JSON.parseArray(jsonArray.toString(), MjLifeBean::class.java)
            ZipWeatherBean(mjRealWeatherBean, mjAqiBean, mj15DayWeatherBean, mj24WeatherBean, lifeBeans, mj5AqiBean)
        }.subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                weatherInfo.postValue(it)
                updateWeatherCache(WeatherCacheInfo(city,long,lat,Gson().toJson(it)))
            }, Consumer {

            })

    }


   private  fun updateWeatherCache(weatherCacheInfo: WeatherCacheInfo){
        viewModelScope.launch(Dispatchers.IO) {
            DbHelper.addCityMsg(weatherCacheInfo)
        }
    }

}