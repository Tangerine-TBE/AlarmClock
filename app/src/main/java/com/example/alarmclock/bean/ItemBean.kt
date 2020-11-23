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
data class ItemBean(val title: String = "", val icon: Int = 0, val hint: String = "",var isOpen:Boolean=false,var view: View? =null) {

}