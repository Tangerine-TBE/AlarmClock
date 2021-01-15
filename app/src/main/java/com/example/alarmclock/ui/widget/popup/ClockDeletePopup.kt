package com.example.alarmclock.ui.widget.popup

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.alarmclock.R
import com.tamsiree.rxkit.RxDeviceTool
import kotlinx.android.synthetic.main.diy_clock_delete_popup_window.view.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.weight
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/26 16:40
 * @class describe
 */
class ClockDeletePopup(activity: Activity) : BasePopup(activity,R.layout.diy_clock_delete_popup_window,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
     val mTextView: TextView =view.mDeleteHint
    init {
        animationStyle=R.style.deletePopup
        view?.apply {
            mCancelHint.setOnClickListener {
                this@ClockDeletePopup.dismiss()
            }
        }
    }


}