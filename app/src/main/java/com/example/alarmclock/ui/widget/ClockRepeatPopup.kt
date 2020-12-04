package com.example.alarmclock.ui.widget

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.model.DataProvider
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
class ClockRepeatPopup(activity: Activity) : BasePopup(activity,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
    private val mActivity=activity
    private  val mView: View = LayoutInflater.from(activity).inflate(R.layout.diy_clock_popup_window, null)
     var mRepeatCountAdapter:RepeatCountAdapter
    init {
        contentView = mView
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isFocusable = true
        isOutsideTouchable = false
        animationStyle=R.style.selectPopup

        mView.apply {
            mSelectContainer.layoutManager=LinearLayoutManager(mActivity)
            mRepeatCountAdapter= RepeatCountAdapter()
            mRepeatCountAdapter.setList(DataProvider.repeatData)
            mSelectContainer.adapter=mRepeatCountAdapter
        }

        setOnDismissListener {
            mOutValueAnimator?.start()
        }
    }



}