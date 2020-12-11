package com.example.alarmclock.util

import android.os.CountDownTimer

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.util
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/12/8 15:48:04
 * @class describe
 */
object TimerUtil {
    fun startCountDown(duration:Long,step:Long,timer:()->Unit):CountDownTimer=
       object : CountDownTimer(duration, step) {
            override fun onFinish() {
                timer.invoke()
            }
            override fun onTick(millisUntilFinished: Long) {
            }
        }



}