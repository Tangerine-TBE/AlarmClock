package com.twx.module_weather.db

import androidx.core.content.contentValuesOf
import com.twx.module_weather.domain.WeatherCacheInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.litepal.LitePal

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.db
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/18 13:35:58
 * @class describe
 */
object DbHelper {

    /**
     * 添加天气缓存信息
     * @param weatherInfo WeatherCacheInfo
     */
   suspend fun addCityMsg(weatherInfo:WeatherCacheInfo){
      coroutineScope{
           val cityMsg = LitePal.where("city=?", weatherInfo.city).find(WeatherCacheInfo::class.java)
           if (cityMsg.size > 0) {
               LitePal.updateAll(WeatherCacheInfo::class.java, contentValuesOf("weathermsg" to weatherInfo.weatherMsg),"city=?",weatherInfo.city)
           } else {
               weatherInfo.save()
           }
       }
    }

    /**
     * 删除天气缓存信息
     * @param weatherInfo WeatherCacheInfo
     */
    suspend fun deleteCityMsg(city:String?){
     coroutineScope {
         LitePal.deleteAll(WeatherCacheInfo::class.java, "city=?",city)
     }

    }

    /**
     * 查询天气缓存信息
     *
     * @return MutableList<WeatherCacheInfo>
     */
    suspend fun findCityMsg(): MutableList<WeatherCacheInfo> = withContext(Dispatchers.IO) {
           LitePal.findAll(WeatherCacheInfo::class.java)
        }


    /**
     * 查询天气缓存信息
     *
     * @return MutableList<WeatherCacheInfo>
     */
    suspend fun findCityWeatherMsg(city: String)= withContext(Dispatchers.IO){LitePal.where("city=?", city).find(WeatherCacheInfo::class.java)}





}