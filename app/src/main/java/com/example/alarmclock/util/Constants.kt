package com.example.alarmclock.util

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.util
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/17 14:24
 * @class describe
 */
object Constants {
    //Intent跳转动作
    const val CLOCK_ACTION="clock_action"
    const val CLOCK_INFO="clock_info"
    const val CLOCK_VIBRATION="clock_vibration"

    //墨迹天气
    const val WEATHER_URL = "http://aliv8.data.moji.com/whapi/json/aliweather/"
    //Toast是否提示
    const val TOAST_SCREEN_TIME="toast_screen_time"
    //通知渠道id
    const val SERVICE_CHANNEL_ID_FOREGROUND="service_channel_id_foreground"
    const val SERVICE_CHANNEL_ID_TELL_TIME="service_channel_id_tell_time"
    const val SERVICE_CHANNEL_ID_TIME_OUT="service_channel_id_time_out"
    //通知id
    const val SERVICE_ID_FOREGROUND=1
    const val SERVICE_ID_TELL_TIME=2
    const val SERVICE_ID_TELL_OUT=3

    //通知广播
    const val ACTION_CUSTOM_VIEW_OPTIONS_CANCEL="action_custom_view_options_cancel"
    //主题选择
    const val CURRENT_THEME="current_theme"

    //----------------------------SP缓存----------------------
    //整点报时时间
    const val SP_TELL_TIME_LIST="tell_time_list"
    //天气
    const val SP_WEATHER_LIST="sp_weather_list"

    //----------------------------配置-----------------------------
    //主题
    const val THEME_ONE=0
    const val THEME_TWO=1
    const val THEME_THREE=2
    const val THEME_FOUR=3
    //设置
    const val SET_SHOW_LANDSCAPE="set_show_landscape"
    const val SET_SHOW_HOUR24="set_show_hour24"
    const val SET_SHOW_SECOND="set_show_second"
    const val SET_SHOW_TIME="set_show_time"
    //查询条件
    const val CONDITION="setClockCycle=? and clockTimeHour=? and clockTimeMin=?"
    const val CONDITION_TWO="setClockCycle=? and clockTimeHour=? and clockTimeMin=? and setDiyClockCycle=?"

}