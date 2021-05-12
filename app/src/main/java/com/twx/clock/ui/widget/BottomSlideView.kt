package com.twx.clock.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.RelativeLayout

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.widget
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/12/8 16:09:58
 * @class describe
 */
class BottomSlideView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN->{
             mOnDownListener?.onDown()

            }
            MotionEvent.ACTION_UP->{

            }
            MotionEvent.ACTION_MOVE->{

            }
        }
        return super.onTouchEvent(event)

    }

    private var mOnDownListener: OnDownListener?=null
    fun setOnDownListener(listener: OnDownListener){
        mOnDownListener=listener
    }

    interface OnDownListener{
        fun onDown()
    }


}