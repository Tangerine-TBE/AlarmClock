package com.example.alarmclock.bean

import org.litepal.crud.LitePalSupport
import java.io.Serializable

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.bean
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/12/11 9:38:04
 * @class describe
 */
data class TellTimeBean(var time:Int=1,var timeHint:String="1",var type:Int=0)
    : LitePalSupport(), Serializable {
}