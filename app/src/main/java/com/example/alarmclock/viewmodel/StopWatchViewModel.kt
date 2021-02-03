package com.example.alarmclock.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alarmclock.bean.StopWatchTimeBean
import com.example.alarmclock.util.BtState
import java.util.*
import kotlin.collections.ArrayList

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
    private var itemSign:StopWatchTimeBean?=null
    val itemSignContext by lazy {
        MutableLiveData<MutableList<StopWatchTimeBean>>()
    }



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
                    itemSign=StopWatchTimeBean(minute,second,millisecond)
                    number.postValue(itemSign)
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


    private val list:MutableList<StopWatchTimeBean> = ArrayList()

    private var id=0
    private var oldData:StopWatchTimeBean?=null
    fun getItemSign(){
        itemSign?.let { newData->
            id++
            newData.id=id
            oldData?.let {
                newData.date=differenceValue(newData,it)
            }
            list.add(0, newData)
            oldData=newData
            itemSignContext.value=list
        }
    }

    private fun differenceValue(newData:StopWatchTimeBean,oldData:StopWatchTimeBean):StopWatchTimeBean{
        var newMil=newData.mil
        var newSec=newData.second
        var newMin=newData.min

        var oldMil=oldData.mil
        var oldSec=oldData.second
        var oldMin=oldData.min

        var realMil=0
        var realSec=0
        var realMin=0

        if (newMil >= oldMil) {
            realMil=newMil-oldMil
            if (newSec >= oldSec) {
                realSec = newSec - oldSec
                realMin = newMin - oldMin
            } else {
                realSec = newSec + 60 - oldSec
                realMin = newMin - oldMin - 1
            }

        } else {
            realMil=newMil+100-oldMil
            if (newSec >= oldSec) {
                realSec = newSec - oldSec-1
                realMin = newMin - oldMin
            } else {
                realSec = newSec + 60 - oldSec
                realMin = newMin - oldMin - 1
            }
        }
        return StopWatchTimeBean(realMin,realSec,realMil)
    }



    fun cleanItemList(){
        list.clear()
        itemSignContext.value = list
        id=0
        minute=0
        second=0
        millisecond=0
    }


}