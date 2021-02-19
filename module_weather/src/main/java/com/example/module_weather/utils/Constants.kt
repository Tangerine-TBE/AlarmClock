package com.example.module_weather.utils

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.utils
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/18 11:53:20
 * @class describe
 */
object Constants {
    //墨迹天气
    const val WEATHER_URL = "http://aliv8.data.moji.com/whapi/json/aliweather/"

    //天气图片
    const val MJ_BG = "mj_bg"
    const val MJ_ICON = "mj_icon"
    const val MJ_LAGER_ICON = "mj_lager_icon"
    const val MJ_COLOR = "mj_color"

    const val MJ_Day = "mj_day"
    const val MJ_Night = "mj_night"




    // 坐标转换
    const val ADDRESS_URL= "http://api.map.baidu.com/geocoding/"
    const val ADDRESS = "v3/?address=%s&output=json&ak=ZOt3GqbrehIpGOcGVhWiA3a0lHo3KdsF&mcode=23:4A:FB:54:00:E7:28:E3:26:47:CB:E4:C7:FC:C8:7E:62:51:DF:D0;com.tiantian.tianqi"


    //黄历
    const val HUANG_LI: String= "https://jisuhlcx.market.alicloudapi.com/"
    //黄历key
    const val HUANG_LI_KEY = "APPCODE dad203ec1a164924b728fd76d67429f7"


    //intent key
    const val WEATHER_INFO="WEATHER_INFO"


    //sp  key
    const val SP_HUANG_LI_DATA="HUANG_LI_DATA"
    const val SP_CACHE_TIME="SP_CACHE_TIME"

}