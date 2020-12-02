package com.example.alarmclock.bean

import android.view.View

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.bean
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/16 16:48
 * @class describe
 */
data class ItemBean(var title: String = "", var icon: Int = 0, var hint: String = "",var week:Int=0,var isOpen:Boolean=false,var view: View? =null) {

}