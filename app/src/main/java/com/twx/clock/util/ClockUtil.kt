package com.twx.clock.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.core.content.contentValuesOf
import com.twx.clock.bean.ClockBean
import com.twx.clock.bean.DiyClockCycleBean
import com.twx.clock.bean.TellTimeBean
import com.twx.clock.broadcast.AlarmClockReceiver
import com.twx.module_base.base.BaseApplication
import com.twx.module_base.util.LogUtils
import com.google.gson.Gson
import com.tamsiree.rxkit.RxConstTool
import com.tamsiree.rxkit.RxTimeTool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.litepal.LitePal
import java.util.*


/**
 * @name AlarmClock
 * @class name：com.example.module_base.util.top
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/26 18:59
 * @class describe
 */
class ClockUtil {
    companion object {
        fun setClockState(itemBean: ClockBean, isOpen: Boolean): ContentValues {
            return contentValuesOf(
                "clockTimeHour" to itemBean.clockTimeHour,
                "clockTimeMin" to itemBean.clockTimeMin,
                "clockOpen" to isOpen,
                "closeClockWay" to itemBean.closeClockWay,
                "clockTimestamp" to itemBean.clockTimestamp,
                "setClockCycle" to itemBean.setClockCycle,
                "setVibration" to itemBean.setVibration,
                "setDeleteClock" to itemBean.setDeleteClock,
                "setDiyClockCycle" to itemBean.setDiyClockCycle
            )
        }

        fun getCurrentTimeHint(it: Date): String {
            var hour = RxTimeTool.getIntervalByNow(
                RxTimeTool.milliseconds2Date(it.time),
                RxConstTool.TimeUnit.HOUR
            )
            var min = RxTimeTool.getIntervalByNow(
                RxTimeTool.milliseconds2Date(it.time),
                RxConstTool.TimeUnit.MIN
            )
            if (it.after(Date())) {
                min -= hour * 60
            } else {
                hour = 24 - hour
                if (hour == 24L) {
                    hour -= 1
                    min = 60 - min - 1
                } else {
                    hour -= 1

                    min = 60 - min % 60
                }
            }
            return if (hour == 0L) {
                if (min == 0L) "不到1分钟后响铃" else "${min}分钟后响铃"
            } else {
                "${hour}小时${min}分钟后响铃"
            }
        }

        fun getBetweenDay(it: DiyClockCycleBean, calender: Calendar): Int {
            val list = it.list
            val week = calender.get(Calendar.DAY_OF_WEEK)
            var day: Int
            if (list.size == 1) {
                day = weekBetweenDay(list[0].week, calender.time)
            } else {
                val maxFilter = list.filter { it.week >= week }
                val minFilter = list.filter { it.week < week }
                val realWeak = if (maxFilter.size > 0) {
                    if (maxFilter.size == 1) {
                        if (Date().before(calender.time)) {
                            maxFilter[0].week
                        } else {
                            if (maxFilter[0].week == 7) {
                                maxFilter[0].week
                            } else {
                                val sortedBy = minFilter.sortedBy { it.week }
                                sortedBy[0].week
                            }
                        }
                    } else {
                        if (maxFilter[0].week == week) {
                            if (Date().before(calender.time)) {
                                maxFilter[0].week
                            } else {
                                maxFilter[1].week
                            }
                        } else {
                            maxFilter[0].week
                        }
                    }
                } else {
                    val sortedBy = minFilter.sortedBy { it.week }
                    sortedBy[0].week
                }
                day = weekBetweenDay(realWeak, calender.time)
            }
            return day
        }


        private fun weekBetweenDay(week: Int, date: Date): Int {
            val currentWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            val duffer = when {
                week > currentWeek -> {
                    if (Date().before(date)) week - currentWeek else week - currentWeek - 1
                }
                week == currentWeek -> {
                    if (Date().before(date))
                        0
                    else
                        6
                }
                week < currentWeek -> {
                    if (Date().before(date))
                        week - currentWeek + 7
                    else
                        week - currentWeek + 6
                }
                else -> {
                    0
                }
            }
            LogUtils.i("-------between-------     $duffer     -------------")
            return duffer
        }


        //查询闹钟列表
        suspend fun queryOpenClick(): MutableList<ClockBean> {
            return withContext(Dispatchers.IO) {
                LitePal.findAll(ClockBean::class.java)
            }
        }

        //设置闹钟Id
        private fun setClockId(clockBean: ClockBean): Int {
            return if (clockBean.setClockCycle == 3) {
                val data =
                        Gson().fromJson(clockBean.setDiyClockCycle, DiyClockCycleBean::class.java)
                data?.run {
                    val stringBuffer = StringBuffer()
                    if (list.size != 0) list.forEach { stringBuffer.append(it.icon) }
                    val longNumber =
                            "${clockBean.clockTimeHour}${clockBean.clockTimeMin}${clockBean.setClockCycle}${stringBuffer}".toLong() / 250
                    longNumber.toInt()
                }?: "${clockBean.clockTimeHour}${clockBean.clockTimeMin}${clockBean.setClockCycle}".toInt()
            } else
                "${clockBean.clockTimeHour}${clockBean.clockTimeMin}${clockBean.setClockCycle}".toInt()
        }

        //开启闹钟
       suspend fun openClock(clockBean: ClockBean) {
            withContext(Dispatchers.IO) {
                val bundle = Bundle()
                bundle.putSerializable(Constants.CLOCK_INFO, clockBean)
                val intent = Intent(BaseApplication.getContext(), AlarmClockReceiver::class.java)
                intent.putExtra(Constants.CLOCK_INFO, bundle)
                setAlarm(intent,setClockId(clockBean),clockBean.clockTimeHour,clockBean.clockTimeMin)
            }
        }


        //取消闹钟
        suspend fun stopAlarmClock(clockBean: ClockBean) {
            withContext(Dispatchers.IO) {
                stopAlarm(setClockId(clockBean))
            }

        }



        //开启整点报时
        fun openTellTime(tellTimeBean: TellTimeBean) {
            val bundle = Bundle()
            bundle.putSerializable(Constants.TELL_TIME_INFO, tellTimeBean)
            val intent = Intent(BaseApplication.getContext(), AlarmClockReceiver::class.java)
            intent.putExtra(Constants.TELL_TIME_INFO, bundle)
            setAlarm(intent,tellTimeBean.time,tellTimeBean.time,0)

        }


        fun stopTellTime(tellTimeBean: TellTimeBean){
            stopAlarm(tellTimeBean.time)
        }

        //设置定时器
        private   fun setAlarm(intent: Intent,alarmId:Int,hour:Int,min:Int) {
            val sender = PendingIntent.getBroadcast(
                    BaseApplication.getContext(),
                    alarmId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )
            val systemTime = System.currentTimeMillis()
            var firstTime = SystemClock.elapsedRealtime()
            val am =
                    BaseApplication.getContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.timeZone = TimeZone.getTimeZone("GMT+8")
            calendar[Calendar.HOUR_OF_DAY] = hour
            calendar[Calendar.MINUTE] = min
            calendar[Calendar.SECOND] = 0
            var selectTime = calendar.timeInMillis
            if (systemTime > selectTime) {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                selectTime = calendar.timeInMillis
            }
            val time = selectTime - systemTime
            firstTime += time
            am.setWindow(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 0, sender)
        }

        //取消定时器
        private fun stopAlarm(id:Int) {
            val intent = Intent(BaseApplication.getContext(), AlarmClockReceiver::class.java)
            val sender =
                    PendingIntent.getBroadcast(BaseApplication.getContext(), id, intent, 0)
            val am =
                    BaseApplication.getContext()
                            .getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.cancel(sender)
        }



        //添加日历提醒
         suspend   fun addClockCalendarEvent(clockBean: ClockBean) {
           addEvent(clockBean)
        }


        private   fun setRepeatRule(clockBean: ClockBean):String=
            when (clockBean.setClockCycle){
                0 ->{
                    ""
                }
                1 -> {
                    "FREQ=WEEKLY;INTERVAL=2;UNTIL=20401230T000000Z;WKST=SU;BYDAY=MO,TU,WE,TU,FR"
                }
                2 -> "FREQ=DAILY;UNTIL=20401230T000000Z"
                3 -> {
                    val diyClockCycle = clockBean.setDiyClockCycle
                    Gson().fromJson(diyClockCycle, DiyClockCycleBean::class.java)?.let {
                        val stringBuffer = StringBuffer()
                        it.list.forEach {
                            stringBuffer.append("${it.byday},")
                        }
                        "FREQ=WEEKLY;INTERVAL=2;UNTIL=20401230T000000Z;WKST=SU;BYDAY="+stringBuffer.substring(0,stringBuffer.length-1)
                    }?:""

                }
                else->""
            }


        //设置日历事件
        private suspend fun addEvent(clockBean: ClockBean) {
            if (CheckPermissionUtil.lacksPermissions()) {
                val repeatRule = setRepeatRule(clockBean)
                LogUtils.i("-----addEvent--------$repeatRule---")
                var min = clockBean.clockTimeMin.toString()
                CalendarUtil.addCalendarEvent(
                        BaseApplication.getContext(),
                        "闹钟提醒",
                        "来自${clockBean.clockTimeHour}时${if (min.length == 1) "0$min" else "$min"}分的闹钟",
                         repeatRule,
                        clockBean.clockTimeHour,
                        clockBean.clockTimeMin
                )
            }
        }

        //删除日历提醒
       suspend  fun deleteCalendarEvent(clockBean: ClockBean) {
            withContext(Dispatchers.IO) {
                if (CheckPermissionUtil.lacksPermissions()) {
                    var min = clockBean.clockTimeMin.toString()
                    CalendarUtil.deleteCalendarEvent(
                        BaseApplication.getContext(),
                        "来自${clockBean.clockTimeHour}时${if (min.length == 1) "0$min" else "$min"}分的闹钟",
                    )
                }
            }
        }
    }


}