package com.example.alarmclock.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.text.TextUtils
import androidx.core.content.ContextCompat.startForegroundService
import androidx.lifecycle.LifecycleService
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import com.example.alarmclock.bean.NotificationBean
import com.example.alarmclock.bean.TimeListBean
import com.example.alarmclock.notification.NotificationFactory
import com.example.alarmclock.present.impl.TellTimePresentImpl
import com.example.alarmclock.ui.activity.LockScreenActivity
import com.example.alarmclock.util.ClockUtil
import com.example.alarmclock.util.Constants
import com.example.alarmclock.view.ITellTimeCallback
import com.example.module_base.base.BaseApplication
import com.example.module_base.util.LogUtils
import com.example.module_base.util.SPUtil
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class TellTimeService : LifecycleService(), ITellTimeCallback {

    private val mTellTimeBroadcastReceiver by lazy { TellTimeBroadcastReceiver() }
    private val mLockIntent by lazy { Intent(this, LockScreenActivity::class.java) }

    companion object{
        fun  startTellTimeService(context: Context){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                startForegroundService(context,Intent(context,TellTimeService::class.java))
            else context.startService(Intent(context, TellTimeService::class.java))
        }
    }


    override fun onCreate() {
        super.onCreate()
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_POWER_CONNECTED)
        }
        registerReceiver(mTellTimeBroadcastReceiver, intentFilter)
        TellTimePresentImpl.registerCallback(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val createNotification =
                    NotificationFactory.getInstance()
                            .createNotificationChannel(Constants.SERVICE_CHANNEL_ID_FOREGROUND, "前台通知")
                            .foregroundNotification(
                                    NotificationBean(
                                            Constants.SERVICE_CHANNEL_ID_FOREGROUND,
                                            "悬浮闹钟",
                                            "悬浮闹钟正在为您提供服务",
                                            R.mipmap.ic_launcher
                                    )
                            )
            startForeground(Constants.SERVICE_ID_FOREGROUND, createNotification)
        }
        LogUtils.i("服务被创建了----------@------->")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.i("服务开始了-----------@-------->")
        GlobalScope.launch(Dispatchers.Main) {
            val queryOpenClick = ClockUtil.queryOpenClick()
            queryOpenClick?.let { it ->
                it.forEach {
                    if (it.clockOpen) ClockUtil.openClock(it)
                 else ClockUtil.stopAlarmClock(it)
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }


    inner class TellTimeBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent?.action) {
                Intent.ACTION_TIME_TICK -> {
                    onTellTime()
                }
               Intent.ACTION_SCREEN_ON->{
                   LogUtils.i("ACTION_SCREEN_ON-----------#-------->")
                   onLockScreenActivity()
               }
                Intent.ACTION_SCREEN_OFF->{
                    LogUtils.i("ACTION_SCREEN_OFF-----------#-------->")
                    onLockScreenActivity()
                }
            }
        }
    }

    private fun onLockScreenActivity(){
        if (SPUtil.getInstance().getBoolean(Constants.SET_SHOW_TIME)) {
            startActivity(mLockIntent.apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            })
        }
    }


    private fun onTellTime() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min: Int = calendar.get(Calendar.MINUTE)
        val listData = SPUtil.getInstance().getString(Constants.SP_TELL_TIME_LIST)
        if (!TextUtils.isEmpty(listData)) {
            val timeListData = Gson().fromJson(listData, TimeListBean::class.java)
            val topList = timeListData.topList
            val bottomList = timeListData.bottomList
            if (topList.size != 0) {
                for (itemBean in topList) {
                    if ("$hour$min" == "${itemBean.title}0") {
                        topList.remove(itemBean)
                        saveTimeData(topList, bottomList, hour)
                        break
                    }
                }
            }
            if (bottomList.size != 0) {
                for (itemBean in bottomList) {
                    //"$hour$min" == "${itemBean.title}0"
                    if ("$hour$min" == "${itemBean.title}0") {
                        bottomList.remove(itemBean)
                        saveTimeData(topList, bottomList, hour)
                        break
                    }
                }
            }

        }
    }
    private fun saveTimeData(topList: MutableList<ItemBean>, bottomList: MutableList<ItemBean>,hour:Int) {
        val createNotification = NotificationFactory.getInstance().createNotificationChannel(Constants.SERVICE_CHANNEL_ID_TELL_TIME, "整点报时")
            .diyNotification(NotificationBean(Constants.SERVICE_CHANNEL_ID_TELL_TIME, "整点报时", "现在是${hour}点整", R.mipmap.ic_launcher))
        NotificationFactory.mNotificationManager.notify(
            Constants.SERVICE_ID_TELL_TIME,
            createNotification)

        startService(Intent(this,MusicService::class.java))
        Gson().toJson(TimeListBean(topList, bottomList)).let {
            SPUtil.getInstance().putString(Constants.SP_TELL_TIME_LIST, it)
            TellTimePresentImpl.getTellTimeLists(it)
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mTellTimeBroadcastReceiver)
        TellTimePresentImpl.unregisterCallback(this)
        startService(Intent(BaseApplication.getContext(), TellTimeService::class.java))
        LogUtils.i("--------------onDestroy---------------->")
    }

    override fun onLoadTimeList(data: String) {
    }


}