package com.feisu.noise.adcontrol

import com.feisukj.base_library.ad.ADConstants
import com.feisukj.base_library.utils.SPUtil

/**
 * 获得锁住音乐开关
 */
val isLockMusicSwitch by lazy {
    val typeBean=getPageControlData(ADConstants.ALL_NEW)
    typeBean?.incentive_video?.status?:false
}

/**
 * 获得音乐是否被锁住了
 */
fun isLockForMusic(name:String):Boolean{
    val unLockTime= SPUtil.instance.getLong(name,0L)
    return unLockTime <= System.currentTimeMillis()
}

fun getUnlockMusicDate(name: String):Long{
    return SPUtil.instance.getLong(name,System.currentTimeMillis())
}

/**
 * 将音乐的解锁到期时间存入
 */
fun putUnlockMusicDate(name:String, time:Long){
    SPUtil.instance.putLong(name,time)
}