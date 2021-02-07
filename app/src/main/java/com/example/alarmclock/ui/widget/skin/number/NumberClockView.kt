package com.example.alarmclock.ui.widget.skin.number

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.alarmclock.R
import com.example.alarmclock.topfun.textViewColorTheme
import com.example.alarmclock.topfun.textViewLandSize
import com.example.alarmclock.util.Constants
import com.example.module_base.util.LogUtils
import com.example.module_base.util.SPUtil
import com.tamsiree.rxkit.RxDeviceTool
import com.tamsiree.rxkit.RxTimeTool
import kotlinx.android.synthetic.main.diy_number_clock.view.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.weight
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/18 10:54
 * @class describe
 */
class NumberClockView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr),LifecycleObserver{
    private var mSetTimeJob:Job?=null
    private var mTimeFormat:SimpleDateFormat
    private var isHour24=false
    private var isShowSecond=false
    init {
        LayoutInflater.from(context).inflate(R.layout.diy_number_clock,this,true)
        textViewColorTheme(mHour,mSecond,mHourHint,mSecondHint,mTextMorning,mTextAfternoon)
        isHour24 = SPUtil.getInstance().getBoolean(Constants.SET_SHOW_HOUR24, true)
        isShowSecond = SPUtil.getInstance().getBoolean(Constants.SET_SHOW_SECOND, true)

        //华为  1794------------1080--------
        //谷歌  2824------------1440--------
        //魅族  ----2136------------1080--------
        textViewLandSize(230f,context,mHour,mHourHint)
       textViewLandSize(80f,context,mSecond,mSecondHint)




        LogUtils.i("---NumberClockView----${RxDeviceTool.getScreenHeight(context)}------------${RxDeviceTool.getScreenWidth(context)}--------")

        mTimeFormat=if (isHour24) SimpleDateFormat(if(isShowSecond)"HH:mm:ss" else "HH:mm") else
             SimpleDateFormat(if(isShowSecond)"hh:mm:ss" else "hh:mm")
        mSecondHint.visibility=if (isShowSecond)View.VISIBLE else View.GONE
        mSecond.visibility = if (isShowSecond) View.VISIBLE else View.GONE

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startTime(){
        mSetTimeJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                val currentTime = RxTimeTool.milliseconds2String(System.currentTimeMillis(), mTimeFormat)
                val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                mTextMorning.text=if (hour < 12) "上午" else "下午"
                mTextAfternoon.text=if (hour<12) "下午" else "上午"

                mHour.text = "${currentTime.substring(0..1)}:${currentTime.substring(3..4)}"
                if (isShowSecond) {
                    mSecond.text = ":${currentTime.substring(6..7)}"
                }
                delay(1000)
            }
        }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopTime(){
        mSetTimeJob?.cancel()
    }




}