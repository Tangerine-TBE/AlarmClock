package com.twx.clock.ui.widget.popup

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.twx.clock.R
import com.twx.clock.interfaces.DiyTimePopupListener
import com.twx.clock.repository.DataProvider
import com.twx.clock.ui.adapter.recyclerview.DiyClockTimeAdapter
import com.twx.module_base.base.BasePopup
import kotlinx.android.synthetic.main.diy_select_popup_window.view.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.weight
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/30 17:38
 * @class describe
 */
class ClockDiyPopup(activity: Activity):
    BasePopup(activity,R.layout.diy_select_popup_window,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT) {
    val mDiyClockTimeAdapter: DiyClockTimeAdapter
    init {
        animationStyle = R.style.selectPopup
        view.apply {
            mDiyTimeContainer.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
            mDiyClockTimeAdapter = DiyClockTimeAdapter()
            mDiyClockTimeAdapter.setList(DataProvider.diyWeekList)
            mDiyTimeContainer.adapter = mDiyClockTimeAdapter


            mDiyCancel.setOnClickListener {
                mDiyTimePopupListener?.cancel()
            }


            mDiySure.setOnClickListener {
                mDiyTimePopupListener?.sure()
            }

        }

    }

    private var mDiyTimePopupListener:DiyTimePopupListener?=null
    fun setOnClickListener(listener: DiyTimePopupListener){
        mDiyTimePopupListener=listener
    }


}