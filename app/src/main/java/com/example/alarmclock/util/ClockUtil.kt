package com.example.alarmclock.util

import android.R.id
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.core.content.contentValuesOf
import com.example.alarmclock.bean.ClockBean
import com.example.alarmclock.bean.DiyClockCycleBean
import com.example.alarmclock.broadcast.AlarmClockReceiver
import com.example.alarmclock.model.DataProvider
import com.example.alarmclock.service.TellTimeService
import com.example.module_base.base.BaseApplication
import com.example.module_base.util.DateUtil
import com.example.module_base.util.LogUtils
import com.google.gson.Gson
import com.loc.am
import com.tamsiree.rxkit.RxConstTool
import com.tamsiree.rxkit.RxTimeTool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.litepal.LitePal
import java.time.DayOfWeek
import java.util.*
import kotlin.math.max
import kotlin.math.min


/**
 * @name AlarmClock
 * @class name：com.example.module_base.util.top
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/26 18:59
 * @class describe
 */
class ClockUtil {
    companion object{
        fun setClockState(itemBean:ClockBean,isOpen:Boolean):ContentValues{
            return contentValuesOf(
                    "clockTimeHour" to itemBean.clockTimeHour,
                    "clockTimeMin" to itemBean.clockTimeMin,
                    "clockOpen" to isOpen,
                    "clockTimestamp" to itemBean.clockTimestamp,
                    "setClockCycle" to itemBean.setClockCycle,
                    "setVibration" to itemBean.setVibration,
                    "setDeleteClock" to itemBean.setDeleteClock,
                    "setDiyClockCycle" to itemBean.setDiyClockCycle
            )
        }

        fun  getCurrentTimeHint(it: Date):String{
            var hour = RxTimeTool.getIntervalByNow(RxTimeTool.milliseconds2Date(it.time), RxConstTool.TimeUnit.HOUR)
            var min = RxTimeTool.getIntervalByNow(RxTimeTool.milliseconds2Date(it.time), RxConstTool.TimeUnit.MIN)
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
            } else {"${hour}小时${min}分钟后响铃"}
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


       private fun weekBetweenDay(week: Int,date: Date):Int{
            val currentWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            val duffer = when {
                week > currentWeek -> {
                    if (Date().before(date)) week - currentWeek else week - currentWeek-1
                }
                week==currentWeek -> {
                    if (Date().before(date))
                       0
                    else
                        6
                }
                week < currentWeek -> {
                    if (Date().before(date))
                        week - currentWeek+7
                    else
                        week - currentWeek+6
                }
                else ->{
                    0
                }
            }
            LogUtils.i("-------between-------     $duffer     -------------")
            return duffer
        }


      suspend  fun  queryOpenClick():MutableList<ClockBean>{
            return  withContext(Dispatchers.IO) {
                    LitePal.findAll(ClockBean::class.java)
                }
        }

        fun openClock(clockBean: ClockBean){
            openAlarm(clockBean,clockBean.setClockCycle)
        }

        //开启闹钟
        private fun openAlarm(clockBean: ClockBean, clockType:Int) {
            val bundle = Bundle()
            bundle.putSerializable(Constants.CLOCK_INFO,clockBean)
            val intent =Intent( BaseApplication.getContext(), AlarmClockReceiver::class.java)
            intent.putExtra(Constants.CLOCK_INFO,bundle)
            val clockId = setClockId(clockBean)
            val sender = PendingIntent.getBroadcast(BaseApplication.getContext(), clockId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val systemTime = System.currentTimeMillis()
            var firstTime = SystemClock.elapsedRealtime()
            val am = BaseApplication.getContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.timeZone = TimeZone.getTimeZone("GMT+8")
            calendar[Calendar.HOUR_OF_DAY] = clockBean.clockTimeHour
            calendar[Calendar.MINUTE] = clockBean.clockTimeMin
            calendar[Calendar.SECOND] = 0
            var selectTime = calendar.timeInMillis
            if (systemTime > selectTime) {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                selectTime = calendar.timeInMillis
            }
            val time = selectTime - systemTime
            firstTime += time
            am.setWindow(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 100, sender)
            }

        //取消闹钟
        fun  stopAlarmClock(clockBean: ClockBean){
            val intent =Intent( BaseApplication.getContext(), AlarmClockReceiver::class.java)
            val clockId = setClockId(clockBean)
            LogUtils.i("-------stopAlarmClock------------$clockId-----")
            val sender = PendingIntent.getBroadcast(BaseApplication.getContext(), clockId, intent, 0)
            val am = BaseApplication.getContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.cancel(sender)
        }


        //设置闹钟Id
        private fun setClockId(clockBean: ClockBean): Int {
            return if (clockBean.setClockCycle == 3) {
                val data = Gson().fromJson(clockBean.setDiyClockCycle, DiyClockCycleBean::class.java)
                data.run {
                    val stringBuffer = StringBuffer()
                    if (list.size != 0) list.forEach { stringBuffer.append(it.icon) }
                    val longNumber = "${clockBean.clockTimeHour}${clockBean.clockTimeMin}${clockBean.setClockCycle}${stringBuffer}".toLong() / 250
                    longNumber.toInt()
                }
            } else
                "${clockBean.clockTimeHour}${clockBean.clockTimeMin}${clockBean.setClockCycle}".toInt()
        }
    }

}