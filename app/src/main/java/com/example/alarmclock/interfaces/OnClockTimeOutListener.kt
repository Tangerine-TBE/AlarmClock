package com.example.alarmclock.interfaces

import com.example.alarmclock.bean.ClockBean

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.interfaces
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/30 10:03
 * @class describe
 */
interface OnClockTimeOutListener {
    fun onClockTimeOut(clockList:MutableList<ClockBean>)
}