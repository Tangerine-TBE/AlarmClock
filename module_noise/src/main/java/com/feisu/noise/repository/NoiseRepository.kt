package com.feisu.noise.repository



/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.present.impl
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/25 14:07
 * @class describe
 */
object  NoiseRepository {

   suspend fun getMp3File(url:String?)=RetrofitManager.createNormal().downFile(url)

}