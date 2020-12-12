package com.example.alarmclock.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.alarmclock.R
import com.example.alarmclock.bean.ClockBean
import com.example.alarmclock.bean.DiyClockCycleBean
import com.example.alarmclock.bean.NotificationBean
import com.example.alarmclock.bean.TellTimeBean
import com.example.alarmclock.model.DataProvider
import com.example.alarmclock.notification.NotificationFactory
import com.example.alarmclock.present.impl.TellTimePresentImpl
import com.example.alarmclock.service.MusicService
import com.example.alarmclock.service.TellTimeService
import com.example.alarmclock.util.CalendarUtil
import com.example.alarmclock.util.ClockUtil
import com.example.alarmclock.util.Constants
import com.example.module_base.util.DateUtil
import com.example.module_base.util.LogUtils
import com.google.gson.Gson
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.litepal.LitePal
import java.util.*

class AlarmClockReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val clockBundle = intent.getBundleExtra(Constants.CLOCK_INFO)
        val tellTimeBundle = intent.getBundleExtra(Constants.TELL_TIME_INFO)
        //对闹钟的处理
        clockBundle?.let { it ->
            val clockBean = it[Constants.CLOCK_INFO] as? ClockBean
            clockBean?.let {
                //响铃闹钟的动作
                GlobalScope.launch(Dispatchers.Main) {
                    val async = async {
                        withContext(Dispatchers.IO) {
                            clockTypeAction(clockBean, context)
                        }
                    }
                    val list =
                        withContext(Dispatchers.IO) {
                            async.await()
                            LitePal.findAll(ClockBean::class.java)

                        }
                    EventBus.getDefault().post(list)
                }
            }
        }

        //对整点报时处理
        tellTimeBundle?.let { it ->
            val tellTimeBean = it[Constants.TELL_TIME_INFO] as? TellTimeBean
            tellTimeBean?.let {
                val createNotification = NotificationFactory.getInstance()
                    .createNotificationChannel(Constants.SERVICE_CHANNEL_ID_TELL_TIME, "整点报时")
                    .diyNotification(
                        NotificationBean(
                            Constants.SERVICE_CHANNEL_ID_TELL_TIME,
                            "整点报时",
                            "现在是${it.time}点整",
                            R.mipmap.ic_launcher
                        )
                    )
                NotificationFactory.mNotificationManager.notify(
                    Constants.SERVICE_ID_TELL_TIME,
                    createNotification
                )
                context.startService(Intent(context, MusicService::class.java))

                GlobalScope.launch(Dispatchers.Main) {
                    val deleteEvent = withContext(Dispatchers.IO) {
                        CalendarUtil.deleteCalendarEvent(context, "现在是${it.time}点整")
                    }
                    val deleteCount = withContext(Dispatchers.IO) {
                        if (deleteEvent == 1)
                            LitePal.deleteAll(
                            TellTimeBean::class.java,
                            "time=?",
                            "${it.time}"
                        ) else 0
                    }
                    if (deleteCount >= 1){
                        TellTimePresentImpl.getTellTimeLists("")
                    }

                }
            }

        }
    }
}


private fun showNotificationMusic(it: ClockBean, context: Context) {
    val createNotification = NotificationFactory.getInstance()
        .createNotificationChannel(Constants.SERVICE_CHANNEL_ID_TIME_OUT, "闹钟来了")
        .diyNotification(
            NotificationBean(
                Constants.SERVICE_CHANNEL_ID_TIME_OUT, "闹钟来了"
                , "现在是${it.clockTimeHour}点${it.clockTimeMin}分", R.mipmap.ic_launcher
            )
        )
    NotificationFactory.mNotificationManager.notify(
        Constants.SERVICE_ID_TELL_OUT,
        createNotification
    )
    val intentService = Intent(context, MusicService::class.java).apply {
        putExtra(Constants.CLOCK_VIBRATION, it.setVibration)
        putExtra(Constants.CLOCK_HOUR, it.clockTimeHour)
        putExtra(Constants.CLOCK_MIN, it.clockTimeMin)
    }
    context.startService(intentService)
}


private suspend fun clockTypeAction(clockBean: ClockBean, context: Context) {
    clockBean.apply {
        if (setDeleteClock) {
            showNotificationMusic(clockBean, context)
            LitePal.deleteAll(
                ClockBean::class.java,
                Constants.CONDITION,
                "$setClockCycle",
                "$clockTimeHour",
                "$clockTimeMin"
            )
            //删除日历提醒
            deleteEvent(clockBean, 3000)
        } else {
            when (setClockCycle) {
                0 -> {
                    showNotificationMusic(clockBean, context)
                    val clockState = ClockUtil.setClockState(clockBean, false)
                    LitePal.updateAll(
                        ClockBean::class.java, clockState, Constants.CONDITION,
                        "$setClockCycle",
                        "$clockTimeHour",
                        "$clockTimeMin"
                    )
                    //删除日历提醒
                    deleteEvent(clockBean, 3000)
                    LogUtils.i("--GGG-------更新闹钟${Thread.currentThread().name}---------------")
                }
                1 -> {
                    val currentWeek = DateUtil.getWeekOfDate2(Date())
                    if (currentWeek in DataProvider.weekList)
                        showNotificationMusic(clockBean, context)
                    else
                        deleteEvent(clockBean, 0)
                    TellTimeService.startTellTimeService(context)
                }
                2 -> {
                    showNotificationMusic(clockBean, context)
                    TellTimeService.startTellTimeService(context)
                }
                3 -> {
                    val currentWeek = DateUtil.getWeekOfDate2(Date())
                    val diyClockCycle = clockBean.setDiyClockCycle
                    val dateList = Gson().fromJson(diyClockCycle, DiyClockCycleBean::class.java)
                    dateList.list.forEach {
                        if (currentWeek == it.hint) {
                            showNotificationMusic(clockBean, context)
                        } else {
                            deleteEvent(clockBean, 0)
                        }
                    }
                    TellTimeService.startTellTimeService(context)
                }
            }
        }

    }
}

private suspend fun deleteEvent(clockBean: ClockBean, delayTime: Long) {
    withContext(Dispatchers.IO) {
        delay(delayTime)
        ClockUtil.deleteCalendarEvent(clockBean)
    }
}