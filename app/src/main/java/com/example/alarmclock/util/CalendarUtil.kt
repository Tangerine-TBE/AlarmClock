package com.example.alarmclock.util

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.provider.CalendarContract
import android.text.TextUtils
import androidx.core.content.contentValuesOf
import com.example.alarmclock.util.ClockUtil.Companion.deleteCalendarEvent
import com.example.module_base.util.LogUtils
import kotlinx.coroutines.*
import java.util.*

/**
 * @author wujinming QQ:1245074510
 * @name My Application
 * @class name：com.example.myapplication
 * @class describe
 * @time 2020/12/8 19:17:58
 * @class describe
 */
class CalendarUtil {

    companion object {
        private const val CALANDER_URL = "content://com.android.calendar/calendars"
        private const val CALANDER_EVENT_URL = "content://com.android.calendar/events"
        private const val CALANDER_REMIDER_URL = "content://com.android.calendar/reminders"
        private const val CALENDARS_NAME = "alarm"
        private const val CALENDARS_ACCOUNT_NAME = "alarm@tom.com"
        private const val CALENDARS_ACCOUNT_TYPE = "com.android.exchange"
        private const val CALENDARS_DISPLAY_NAME = "闹钟账户"


        private fun checkCalendarAccount(context: Context): Int {
            val userCursor = context.contentResolver
                .query(Uri.parse(CALANDER_URL), null, null, null, null)
            return try {
                if (userCursor == null) //查询返回空值
                    return -1
                val count = userCursor.count
                if (count > 0) { //存在现有账户，取第一个账户的id返回
                    userCursor.moveToFirst()
                    userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID))
                } else {
                    -1
                }
            } finally {
                userCursor?.close()
            }
        }

        private fun addCalendarAccount(context: Context): Long {
            val timeZone = TimeZone.getDefault()
            val value = ContentValues()
            value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME)
            value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
            value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
            value.put(
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CALENDARS_DISPLAY_NAME
            )
            value.put(CalendarContract.Calendars.VISIBLE, 1)
            value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE)
            value.put(
                CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
                CalendarContract.Calendars.CAL_ACCESS_OWNER
            )
            value.put(CalendarContract.Calendars.SYNC_EVENTS, 1)
            value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.id)
            value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME)
            value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0)


            var calendarUri = Uri.parse(CALANDER_URL)
            calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(
                    CalendarContract.Calendars.ACCOUNT_NAME,
                    CALENDARS_ACCOUNT_NAME
                )
                .appendQueryParameter(
                    CalendarContract.Calendars.ACCOUNT_TYPE,
                    CALENDARS_ACCOUNT_TYPE
                )
                .build()
            val result = context.contentResolver.insert(calendarUri, value)
            return if (result == null) -1 else ContentUris.parseId(result)
        }

        //检查是否已经添加了日历账户，如果没有添加先添加一个日历账户再查询
        private fun checkAndAddCalendarAccount(context: Context): Int {
            val oldId = checkCalendarAccount(context)
            return if (oldId >= 0) {
                oldId
            } else {
                val addId = addCalendarAccount(context)
                if (addId >= 0) {
                    checkCalendarAccount(context)
                } else {
                    -1
                }
            }
        }

        suspend fun addCalendarEvent(context: Context, title: String, description: String?, hour: Int,min : Int) {
            // 获取日历账户的id
            val calId = checkAndAddCalendarAccount(context)
            // 获取账户id失败直接返回，添加日历事件失败
            if (calId < 0) return
            //先删除再添加
            val withContext = withContext(Dispatchers.IO) {
                async {
                    deleteCalendarEvent(context, title)
                }
            }
            withContext.await()
            val mCalendar = Calendar.getInstance()
            mCalendar[Calendar.HOUR_OF_DAY] = hour
            mCalendar[Calendar.MINUTE] = min
            mCalendar[Calendar.SECOND] = 0 //设置开始时间
            val start = mCalendar.time.time

            mCalendar.timeInMillis = start + 5000 //设置终止时间
            val end = mCalendar.time.time
            //插入数据
            val event = contentValuesOf(
                "title" to title,
                "description" to description,
                "calendar_id" to calId,
                CalendarContract.Events.DTSTART to start,
                CalendarContract.Events.DTEND to end,
                CalendarContract.Events.HAS_ALARM to 1,//设置有闹钟提醒
                CalendarContract.Events.EVENT_TIMEZONE to "Asia/Shanghai" //这个是时区，必须有，
            )
            //添加事件
            val newEvent =
                context.contentResolver.insert(Uri.parse(CALANDER_EVENT_URL), event)// 添加日历事件失败直接返回
            newEvent?.let {
                //事件提醒的设定
                val values = contentValuesOf(
                    CalendarContract.Reminders.EVENT_ID to ContentUris.parseId(newEvent),
                    CalendarContract.Reminders.MINUTES to 0,
                    CalendarContract.Reminders.METHOD to CalendarContract.Reminders.METHOD_ALERT
                )
                context.contentResolver.insert(
                    Uri.parse(CALANDER_REMIDER_URL),
                    values
                )// 添加闹钟提醒失败直接返回
            }

        }


    /**
     * 删除日历事件
     */
    fun deleteCalendarEvent(context: Context, description: String): Int {
        val eventCursor =
            context.contentResolver.query(Uri.parse(CALANDER_EVENT_URL), null, null, null, null)
        eventCursor?.let {
            it.use { eventCursor ->
                if (eventCursor.count > 0) {
                    //遍历所有事件，找到description跟需要查询的description一样的项
                    eventCursor.moveToFirst()
                    while (!eventCursor.isAfterLast) {
                        val eventTitle =
                            eventCursor.getString(eventCursor.getColumnIndex("description"))
                        if (!TextUtils.isEmpty(description) && description == eventTitle) {
                            val id =
                                eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID)) //取得id
                            val deleteUri = ContentUris.withAppendedId(
                                Uri.parse(CALANDER_EVENT_URL),
                                id.toLong()
                            )
                            return context.contentResolver.delete(deleteUri, null, null)
                        }
                        eventCursor.moveToNext()
                    }
                }
            }
        }
        return -1
    }

    //全部删除 整点报时提醒
    suspend fun deleteAllCalendarEvent(context: Context, title: String) {
        withContext(Dispatchers.IO) {
            if (CheckPermissionUtil.lacksPermissions()) {
                context.contentResolver.query(Uri.parse(CALANDER_EVENT_URL), null, null, null, null)
                    ?.let {
                        while (it.moveToNext()) {
                            val eventTitle = it.getString(it.getColumnIndex("title"))
                            if (title == eventTitle) {
                                val id =
                                    it.getInt(it.getColumnIndex(CalendarContract.Calendars._ID)) //取得id
                                val deleteUri = ContentUris.withAppendedId(
                                    Uri.parse(CALANDER_EVENT_URL),
                                    id.toLong()
                                )
                                val delete = context.contentResolver.delete(deleteUri, null, null)
                                LogUtils.i("-----deleteAllCalendarEvent---------$delete----------")
                            }
                        }
                    }
            }
        }
    }




}

}