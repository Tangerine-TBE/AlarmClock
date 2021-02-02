package com.example.alarmclock.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alarmclock.bean.StopWatchTimeBean
import com.example.alarmclock.ui.activity.StopWatchActivity
import com.example.alarmclock.util.BtState
import java.util.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.viewmodel
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/2 16:45:31
 * @class describe
 */
class StopWatchViewModel : ViewModel() {


    private var mTimer: Timer? = null
    private var mTimerTask: TimerTask? = null

    private var millisecond = 0
    private var second = 0
    private var minute = 0

    val btState by lazy {
        MutableLiveData<BtState>()
    }

    val number by lazy {
        MutableLiveData<StopWatchTimeBean>()
    }


    fun setBtState(state: BtState) {
        btState.value = state
    }


    fun startTimer() {
        if (mTimer == null) {
            mTimer = Timer()

        }

        if (mTimerTask == null) {
            mTimerTask = object : TimerTask() {
                override fun run() {
                    if (millisecond < 99) {
                        millisecond++
                    } else {
                        millisecond = 0

                        if (second < 59) {
                            second++
                        }
                        else {

                            second = 0

                            if (minute < 59) {
                                minute++
                            }
                            else {
                                btState.postValue(BtState.STOP)
                            }
                        }
                    }
                    number.postValue(StopWatchTimeBean(minute,second,millisecond))
                }
            }
        }
        if (mTimer != null && mTimerTask != null) {
            mTimer?.schedule(mTimerTask, 0, 10)
        }

    }

    fun stopTimer() {
        if (mTimer != null) {
            mTimer?.cancel()
            mTimer = null
        }
        if (mTimerTask != null) {
            mTimerTask?.cancel()
            mTimerTask = null
        }
    }


}