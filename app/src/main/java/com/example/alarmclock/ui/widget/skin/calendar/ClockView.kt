package com.example.alarmclock.ui.widget.skin.calendar

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import kotlin.jvm.JvmOverloads
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleObserver
import com.example.alarmclock.ui.widget.skin.calendar.TabDigit
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.example.alarmclock.R
import com.example.alarmclock.util.Constants
import com.example.module_base.util.SPUtil
import com.example.module_base.util.SizeUtils
import kotlinx.android.synthetic.main.clock.view.*
import kotlinx.coroutines.*
import java.util.*

/**
 * Created by Eugeni on 04/12/2016.
 */
class ClockView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context!!, attrs, defStyleAttr), LifecycleObserver {
    private  var mCharHour: TabDigit
    private  var mCharSecond: TabDigit
    private  var mCharMinute: TabDigit
    private var layout:LinearLayout
    private var isShowSecond=false
    init {
        val view = LayoutInflater.from(context).inflate(R.layout.clock, this, true)
        mCharHour = view.findViewById(R.id.hourTab)
        mCharMinute = view.findViewById(R.id.minuteTab)
        mCharSecond = view.findViewById(R.id.secondTab)
        layout = view.findViewById(R.id.linearLayout2)
        setConfig()
        isShowSecond = SPUtil.getInstance().getBoolean(Constants.SET_SHOW_SECOND, true)
        layout.visibility=if (isShowSecond) View.VISIBLE else View.GONE

    }


    private fun setConfig(){
        val time = Calendar.getInstance()
        val hour = time[Calendar.HOUR_OF_DAY]
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