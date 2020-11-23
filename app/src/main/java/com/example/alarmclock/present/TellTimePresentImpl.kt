package com.example.alarmclock.present

import com.example.alarmclock.bean.ItemBean
import com.example.alarmclock.view.ITellTimeCallback
import com.example.module_base.base.BasePresent

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.present
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/18 18:43
 * @class describe
 */
object  TellTimePresentImpl:BasePresent<ITellTimeCallback>(){
    fun getTellTimeLists(data:String){
        mCallbacks.forEach {
            it.onLoadTimeList(data)
        }
    }
}