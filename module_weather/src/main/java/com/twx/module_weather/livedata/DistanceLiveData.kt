package com.twx.module_weather.livedata

import androidx.lifecycle.LiveData

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.livedata
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/19 16:41:53
 * @class describe
 */
object DistanceLiveData:LiveData<Int>() {

    fun  setTopType(dis:Int){
        value=dis
    }

}