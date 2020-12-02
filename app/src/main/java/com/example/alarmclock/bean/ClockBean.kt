package com.example.alarmclock.bean

import org.litepal.crud.LitePalSupport
import java.io.Serializable

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.bean
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/24 11:58
 * @class describe
 */
data class ClockBean(
    var clockTimeHour: Int=0,
    var clockTimeMin: Int=0,
    var clockOpen:Boolean=false,
    var clockTimestamp:Long=System.currentTimeMillis(),
    var setClockCycle:Int=0,
    var setVibration:Boolean=true,
    var setDeleteClock:Boolean=false,
    var setDiyClockCycle:String=""

):LitePalSupport(),Serializable


data class DiyClockCycleBean(var list:MutableList<ItemBean>)




