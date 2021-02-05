package com.example.alarmclock.ui.widget.popup

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import com.example.alarmclock.ui.adapter.recyclerview.RepeatCountAdapter
import kotlinx.android.synthetic.main.diy_clock_popup_window.view.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.weight
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/24 18:36
 * @class describe
 */
class ClockRepeatPopup(activity: Activity,title:String,list:MutableList<ItemBean>) : BasePopup(activity,R.layout.diy_clock_popup_window,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
    var mRepeatCountAdapter:RepeatCountAdapter
    init {
        animationStyle=R.style.selectPopup
        view.apply {
            mSelectTitle.text=title
            mSelectContainer.layoutManager=LinearLayoutManager(activity)
            mRepeatCountAdapter= RepeatCountAdapter()
            mRepeatCountAdapter.setList(list)
            mSelectContainer.adapter=mRepeatCountAdapter
        }

    }

}