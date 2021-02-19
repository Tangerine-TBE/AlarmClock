package com.example.module_weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.module_base.base.BaseViewModel
import com.example.module_base.util.gsonHelper
import com.example.module_weather.db.DbHelper
import com.example.module_weather.domain.WeatherCacheInfo
import com.example.module_weather.domain.ZipWeatherBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.viewmodel
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/19 10:49:31
 * @class describe
 */
class AirViewModel:BaseViewModel() {

    val weatherMsg by lazy {
        MutableLiveData<ZipWeatherBean>()
    }

    fun getAqiInfo(msg: String?) {
        gsonHelper<ZipWeatherBean>(msg)?.let {
            weatherMsg.value=it
        }
    }

}