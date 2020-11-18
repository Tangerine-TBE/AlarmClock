package com.example.alarmclock.ui.weight

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.alarmclock.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.diy_number_clock.view.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.weight
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/18 10:54
 * @class describe
 */
class NumberClock @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.diy_number_clock, this, true)
    }

    fun setTime(currentTime:String){
        mHour.text = currentTime.substring(0..1)
        mMinute.text = currentTime.substring(3..4)
        mSecond.text = currentTime.substring(6..7)
    }
}