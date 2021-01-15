package com.example.alarmclock.ui.widget.popup

import android.app.ActionBar
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.R
import com.example.alarmclock.interfaces.DiyTimePopupListener
import com.example.alarmclock.model.DataProvider
import com.example.alarmclock.ui.adapter.recyclerview.DiyClockTimeAdapter
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