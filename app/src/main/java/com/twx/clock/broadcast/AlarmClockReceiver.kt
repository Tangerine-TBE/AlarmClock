package com.twx.clock.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.twx.clock.R
import com.twx.clock.bean.ClockBean
import com.twx.clock.bean.DiyClockCycleBean
import com.twx.clock.bean.NotificationBean
import com.twx.clock.bean.TellTimeBean
import com.twx.clock.repository.DataProvider
import com.twx.clock.notification.NotificationFactory
import com.twx.clock.service.MusicService
import com.twx.clock.service.TellTimeService
import com.twx.clock.util.ClockUtil
import com.twx.clock.util.Constants
import com.twx.module_base.util.SpeakUtil
import com.twx.module_base.util.DateUtil
import com.twx.module_base.util.LogUtils
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
                    .normalNotification(
                        NotificationBean(
                            Constants.SERVICE_CHANNEL_ID_TELL_TIME,
                            "整点报时",
                            "现在是${it.time}点整",
                            R.mipmap.ic_launcher
                        ,false
                        )
                    )
                NotificationFactory.mNotificationManager.notify(
                    Constants.SERVICE_ID_TELL_TIME,
                    createNotification
                )
             SpeakUtil.speakText(it.timeText)
            }

        }
    }
}


private fun showNotificationMusic(it: ClockBean, context: Context) {
    when(it.closeClockWay){
        0->{
            val createNotification = NotificationFactory.getInstance()
                .createNotificationChannel(Constants.SERVICE_CHANNEL_ID_ClOCK, "闹钟来了")
                .normalNotification(
                    NotificationBean(
                        Constants.SERVICE_CHANNEL_ID_ClOCK, "闹钟关闭"
                        , "点击关闭闹钟", R.mipmap.ic_launcher,true
                    )
                )
            NotificationFactory.mNotificationManager.notify(
                Constants.SERVICE_ID_CLOCK,
                createNotification
            )
        }
    }
    val intentService = Intent(context, MusicService::class.java).apply {
        putExtra(Constants.CLOCK_VIBRATION, it.setVibration)
        putExtra(Constants.CLOCK_HOUR, it.clockTimeHour)
        putExtra(Constants.CLOCK_MIN, it.clockTimeMin)
        putExtra(Constants.CLOSE_TYPE,it.closeClockWay)
    }
    context?.startService(intentService)
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
                    if (currentWeek in DataProvider.weekList) showNotificationMusic(clockBean, context)
                    TellTimeService.startTellTimeService(context){putExtra(Constants.TELL_TIME_SERVICE,2)}
                }
                2 -> {
                    showNotificationMusic(clockBean, context)
                    TellTimeService.startTellTimeService(context){putExtra(Constants.TELL_TIME_SERVICE,2)}
                }
                3 -> {
                    val currentWeek = DateUtil.getWeekOfDate2(Date())
                    val diyClockCycle = clockBean.setDiyClockCycle
                    Gson().fromJson(diyClockCycle, DiyClockCycleBean::class.java)?.list?.apply {
                            if (size>0) {
                                forEach {
                                    if (currentWeek == it.hint) showNotificationMusic(clockBean, context)
                                }
                        }
                    }
                    TellTimeService.startTellTimeService(context){putExtra(Constants.TELL_TIME_SERVICE,2)}
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