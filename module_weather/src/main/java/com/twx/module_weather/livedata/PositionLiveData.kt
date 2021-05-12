package com.twx.module_weather.livedata

import androidx.lifecycle.LiveData
import com.twx.module_weather.domain.ValueUserAction
import com.twx.module_weather.utils.UserAction

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.livedata
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/19 17:49:36
 * @class describe
 */
object PositionLiveDate : LiveData<ValueUserAction>() {

    init {
      value=  ValueUserAction(UserAction.NONE,0)
    }


    fun setPosition(action:ValueUserAction){
       postValue(action)
    }


}