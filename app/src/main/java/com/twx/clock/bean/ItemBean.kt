package com.twx.clock.bean

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.bean
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/16 16:48
 * @class describe
 */
data class ItemBean(var title: String = "", var icon: Int = 0, var bgcolor:Int=0, var titlecolor:Int=0, var type:Int=0, var hint: String = "", var week:Int=0, var byday:String="", var isOpen:Boolean=false)
{ constructor():this("",  0,  0,0,0,"",0,"",false) }