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

    //整点报时时间表
    const val TELL_TIME_LIST="tell_time_list"

    //通知渠道id
    const val SERVICE_CHANNEL_ID_FOREGROUND="service_channel_id_foreground"
    const val SERVICE_CHANNEL_ID_TELL_TIME="service_channel_id_tell_time"
    //通知id
    const val SERVICE_ID_TELL_TIME=1

    //通知广播
    const val ACTION_CUSTOM_VIEW_OPTIONS_CANCEL="action_custom_view_options_cancel"

    //主题选择
    const val CURRENT_THEME="current_theme"

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

}