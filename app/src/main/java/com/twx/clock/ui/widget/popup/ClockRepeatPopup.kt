package com.twx.clock.ui.widget.popup

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.twx.clock.R
import com.twx.clock.bean.ItemBean
import com.twx.clock.ui.adapter.recyclerview.RepeatCountAdapter
import com.twx.module_base.base.BasePopup
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