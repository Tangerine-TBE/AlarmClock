package com.twx.clock.bean

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.bean
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/2 18:05:20
 * @class describe
 */
data class StopWatchTimeBean(val min:Int,val second:Int,val mil:Int,var id:Int=0,var date:StopWatchTimeBean?=null)
