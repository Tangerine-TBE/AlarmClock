package com.example.alarmclock.ui.weight

import android.app.ActionBar
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.R
import com.example.alarmclock.interfaces.DiyTimePopupListener
import com.example.alarmclock.model.DataProvider
import com.example.alarmclock.ui.adapter.recyclerview.DiyClockTimeAdapter
import com.example.module_base.util.LogUtils
import kotlinx.android.synthetic.main.diy_select_popup_window.view.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.weight
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/30 17:38
 * @class describe
 */
class ClockDiyPopup(activity: Activity):BasePopup(activity,ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT) {
    private val mActivity = activity
    private val mView: View = LayoutInflater.from(activity).inflate(R.layout.diy_select_popup_window, null)
      val mDiyClockTimeAdapter: DiyClockTimeAdapter

    init {
        contentView = mView
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isFocusable = true
        isOutsideTouchable = false
        animationStyle = R.style.selectPopup

        mView.apply {
            mDiyTimeContainer.layoutManager = LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false)
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

        setOnDismissListener {
            mOutValueAnimator?.start()
        }

    }

    private var mDiyTimePopupListener:DiyTimePopupListener?=null
    fun setOnClickListener(listener: DiyTimePopupListener){
        mDiyTimePopupListener=listener
    }


}