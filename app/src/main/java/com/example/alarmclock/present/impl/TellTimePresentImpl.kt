package com.example.alarmclock.present.impl

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
    fun getTellTimeLists(){
        mCallbacks.forEach {
            it.onLoadTimeList()
        }
    }
}