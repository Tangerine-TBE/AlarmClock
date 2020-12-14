package com.example.alarmclock.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.example.alarmclock.util.TextViewType

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.widget
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/12/14 17:40:01
 * @class describe
 */
class ClockTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {
    init {
        TextViewType.getTextViewType()?.let {
            typeface=it
        }
    }
}