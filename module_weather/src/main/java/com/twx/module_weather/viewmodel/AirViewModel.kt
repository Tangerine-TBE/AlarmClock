package com.twx.module_weather.viewmodel

import androidx.lifecycle.MutableLiveData
import com.twx.module_base.base.BaseViewModel
import com.twx.module_base.util.gsonHelper
import com.twx.module_weather.domain.ZipWeatherBean

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