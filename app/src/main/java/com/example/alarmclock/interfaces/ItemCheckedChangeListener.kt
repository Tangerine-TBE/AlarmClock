package com.example.alarmclock.interfaces

import com.example.alarmclock.bean.ItemBean

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.Interface
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/26 14:13
 * @class describe
 */
interface ItemCheckedChangeListener<T> {
    fun  onItemChecked(itemBean: T,isCheck: Boolean,position: Int)

    fun onItemClick(itemBean: T,position: Int)
}