package com.example.alarmclock.service

import android.content.*
import android.os.Build
import androidx.core.content.ContextCompat.startForegroundService
import androidx.lifecycle.LifecycleService
import com.example.alarmclock.R
import com.example.alarmclock.bean.NotificationBean
import com.example.alarmclock.bean.TellTimeBean
import com.example.alarmclock.notification.NotificationFactory
import com.example.alarmclock.ui.activity.LockScreenViewActivity
import com.example.alarmclock.ui.widget.desk.NewAppWidget
import com.example.alarmclock.util.ClockUtil
import com.example.alarmclock.util.Constants
import com.example.module_base.util.LogUtils
import com.example.module_base.util.SPUtil
import kotlinx.coroutines.*
import org.litepal.LitePal


class TellTimeService : LifecycleService() {

    private val mTellTimeBroadcastReceiver by lazy { TellTimeBroadcastReceiver() }
    private val mLockIntent by lazy { Intent(this, LockScreenViewActivity::class.java) }
    private val mJob = Job()
    private val mJomScope= CoroutineScope(mJob)

    companion object{
        fun  startTellTimeService(context: Context,intent:Intent.()->Unit){
            val intentService = Intent(context, TellTimeService::class.java)
            intentService.intent()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                startForegroundService(context, intentService)
            }
            else{ context.startService(intentService)}
        }
    }


    override fun onCreate() {
        super.onCreate()
        //启动前台服务
        startService()
        LogUtils.i("服务被创建了----------@------->")
        //广播监听
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_POWER_CONNECTED)
        }
        registerReceiver(mTellTimeBroadcastReceiver, intentFilter)
        //更新小组件
        NewAppWidget.updateWidget(this)

    }



    private fun startService() {
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
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.i("服务开始了-----------@-------->")
        intent?.let {
            when(it.getIntExtra(Constants.TELL_TIME_SERVICE, 0)){
                1->{
                    //设置整点报时
                    setTellTime()
                }
               2->{
                   //设置闹钟
                   setClockEvent()
            }
                else->{
                    //设置整点报时
                    setTellTime()
                    //设置闹钟
                    setClockEvent()
                }

            }

        }
        return super.onStartCommand(intent, flags, startId)
    }

    //设置整点报时
    private fun setTellTime(){
        val isAdd = SPUtil.getInstance().getBoolean(Constants.TELL_TIME_IS_OPEN,true)
        mJomScope.launch {
           val list = LitePal.findAll(TellTimeBean::class.java)
            list?.let { it ->
                if (it.size > 0) {
                    it.forEach {
                        if (isAdd) {
                            ClockUtil.openTellTime(it)
                        } else {
                            ClockUtil.stopTellTime(it)
                        }
                    }
                }
            }
        }
    }

    //设置闹钟
    private fun setClockEvent() {
        mJomScope.launch(Dispatchers.Main) {
            val queryOpenClick = ClockUtil.queryOpenClick()
            queryOpenClick?.let { it ->
                it.forEach {
                    if (it.clockOpen) {
                        ClockUtil.openClock(it)
                    }
                    else {
                        ClockUtil.stopAlarmClock(it)
                        //删除日历提醒
                        ClockUtil.deleteCalendarEvent(it)
                    }
                }
            }
        }
    }


    inner class TellTimeBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent?.action) {
                Intent.ACTION_TIME_TICK -> {
                    LogUtils.i("ACTION_TIME_TICK-----------#-------->")
                    NewAppWidget.updateWidget(context)
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




    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mTellTimeBroadcastReceiver)
        mJob?.cancel()
    }




}