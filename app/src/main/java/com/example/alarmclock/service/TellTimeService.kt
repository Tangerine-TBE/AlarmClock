package com.example.alarmclock.service

import android.R.string
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import com.alibaba.fastjson.JSON
import com.example.alarmclock.bean.ItemBean
import com.example.alarmclock.present.TellTimePresentImpl
import com.example.alarmclock.util.Constants
import com.example.alarmclock.view.ITellTimeCallback
import com.example.module_base.util.LogUtils
import com.example.module_base.util.SPUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.loc.ad
import com.tamsiree.rxkit.view.RxToast
import java.util.*
import kotlin.collections.ArrayList


class TellTimeService : Service(), ITellTimeCallback {

    private val mTellTimeBroadcastReceiver by lazy {
        TellTimeBroadcastReceiver()
    }

    private var mTimingList:MutableList<ItemBean>?=ArrayList()

    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }

    override fun onCreate() {
        super.onCreate()
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
        }
        registerReceiver(mTellTimeBroadcastReceiver,intentFilter)
        TellTimePresentImpl.registerCallback(this)
        LogUtils.i("服务被创建了----------@------->")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.i("服务开始了-----------@-------->")
        return super.onStartCommand(intent, flags, startId)
    }


    inner class TellTimeBroadcastReceiver :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val  calendar= Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val min: Int = calendar.get(Calendar.MINUTE)
            mTimingList?.let {
                for (itemBean in it) {
                    LogUtils.i("TellTimeBroadcastReceiver-----------@-------->${itemBean.title}")
                    if (min==itemBean.title.toInt()){
                        RxToast.normal("${itemBean.title}现在是报时")
                    }
                }
            }
            LogUtils.i("currentTime ${Thread.currentThread().name}------@-----> $hour:$min")
        }

    }




    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mTellTimeBroadcastReceiver)
        TellTimePresentImpl.unregisterCallback(this)

    }

    override fun onLoadTimeList(data: MutableList<ItemBean>) {
        mTimingList= data
    }


}