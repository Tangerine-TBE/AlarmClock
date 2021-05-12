package com.twx.module_weather.livedata

import androidx.lifecycle.LiveData
import com.twx.module_weather.db.DbHelper
import com.twx.module_weather.domain.WeatherCacheInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.livedata
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/18 17:30:10
 * @class describe
 */
object CityListLiveData:LiveData<MutableList<WeatherCacheInfo>>() {

    fun queryCityNumber(){
        GlobalScope.launch(Dispatchers.IO) {
            val findCityMsg = DbHelper.findCityMsg()
            postValue(findCityMsg)
        }
    }

}