package com.example.alarmclock.ui.widget

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.alarmclock.R
import kotlinx.android.synthetic.main.diy_clock_delete_popup_window.view.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.weight
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/26 16:40
 * @class describe
 */
class ClockDeletePopup(activity: Activity) : BasePopup(activity,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
    private val mView=LayoutInflater.from(activity).inflate(R.layout.diy_clock_delete_popup_window,null)
     val mTextView: TextView =mView.mDeleteHint
    init {
        contentView = mView
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isFocusable = true
        isOutsideTouchable = false
        animationStyle=R.style.deletePopup
        initEvent()
    }

    private fun initEvent() {
        mView?.apply {

            mCancelHint.setOnClickListener {
                this@ClockDeletePopup.dismiss()
            }

            setOnDismissListener {
                mOutValueAnimator?.start()
            }

        }
    }


}