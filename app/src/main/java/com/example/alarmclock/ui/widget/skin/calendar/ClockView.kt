package com.example.alarmclock.ui.widget.skin.calendar

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import kotlin.jvm.JvmOverloads
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleObserver
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.example.alarmclock.R
import com.example.alarmclock.topfun.setCurrentThemeColor
import com.example.alarmclock.topfun.setRadiusBg
import com.example.alarmclock.topfun.tabDigitViewColorTheme
import com.example.alarmclock.util.Constants
import com.example.module_base.util.SPUtil
import com.example.module_base.util.SizeUtils
import kotlinx.coroutines.*
import java.util.*

/**
 * Created by Eugeni on 04/12/2016.
 */
class ClockView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context!!, attrs, defStyleAttr), LifecycleObserver {
    private  var mCharHour: TabDigit
    private  var mCharSecond: TabDigit
    private  var mCharMinute: TabDigit
    private var secondLayout:LinearLayout
    private var bgOne:RelativeLayout
    private var bgTwo:RelativeLayout
    private var bgThree:RelativeLayout
    private var   mDrawable = GradientDrawable()
    private var isShowSecond=false
    private var isHour24=false
    init {
        val view = LayoutInflater.from(context).inflate(R.layout.clock, this, true)
        mCharHour = view.findViewById(R.id.hourTab)
        mCharMinute = view.findViewById(R.id.minuteTab)
        mCharSecond = view.findViewById(R.id.secondTab)
        secondLayout = view.findViewById(R.id.secondLayout)
        bgOne = view.findViewById(R.id.bgOne)
        bgTwo = view.findViewById(R.id.bgTwo)
        bgThree = view.findViewById(R.id.bgThree)
        mDrawable.apply {
            cornerRadius=SizeUtils.dip2px(context, 10f).toFloat()
            setColor(ContextCompat.getColor(context, setCurrentThemeColor().bgcolor))
        }

        isShowSecond = SPUtil.getInstance().getBoolean(Constants.SET_SHOW_SECOND, true)
        isHour24 = SPUtil.getInstance().getBoolean(Constants.SET_SHOW_HOUR24, true)


        secondLayout.visibility=if (isShowSecond) View.VISIBLE else View.GONE

        setConfig()
        setRadiusBg(bgOne,bgTwo,bgThree){
            background=mDrawable
        }

    }



    private fun setConfig(){
        val time = Calendar.getInstance()
       val hour=if (isHour24) {
            time[Calendar.HOUR_OF_DAY]
        } else {
            time[Calendar.HOUR]
        }

        val minutes = time[Calendar.MINUTE]
        val seconds = time[Calendar.SECOND]

        mCharHour.apply {
            setChar(hour)

        }
        mCharMinute.apply {
            setChar(minutes)

        }
        mCharSecond.apply {
            setChar(seconds)

        }
        tabDigitViewColorTheme(mCharHour,mCharMinute,mCharSecond)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        mCharHour.sync()
        mCharMinute.sync()
        mCharSecond.sync()
        job?.cancel()
    }

    private var job:Job?=null
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        job = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                val time = Calendar.getInstance()
                val seconds = time[Calendar.SECOND]
                mCharSecond.start()
                mCharSecond.setChar(seconds)
                val minutes = time[Calendar.MINUTE]
                val hour = time[Calendar.HOUR_OF_DAY]
                if (seconds == 59) {
                    /* minutes*/
                    mCharMinute.start()
                    mCharMinute.setChar(minutes)
                    if (minutes == 59) {
                        /* hours*/
                        mCharHour.start()
                        mCharHour.setChar(hour)
                    }
                }
                delay(1000)
            }

        }

    }

}