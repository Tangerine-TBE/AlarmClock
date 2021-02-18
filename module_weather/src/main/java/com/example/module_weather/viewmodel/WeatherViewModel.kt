package com.example.module_weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.module_base.base.BaseViewModel
import com.example.module_weather.db.DbHelper
import com.example.module_weather.domain.WeatherCacheInfo
import com.example.module_weather.livedata.CityListLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.viewmodel
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/18 10:56:09
 * @class describe
 */
class WeatherViewModel:BaseViewModel() {

    val cityList by lazy {
        MutableLiveData<MutableList<WeatherCacheInfo>>()
    }


    fun queryCityNumber(){
        viewModelScope.launch(Dispatchers.IO) {
            val findCityMsg = DbHelper.findCityMsg()
            cityList.postValue(findCityMsg)

        }
    }


}