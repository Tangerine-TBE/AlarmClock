package com.example.alarmclock.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.example.alarmclock.R

/**
 * @name td_horoscope
 * @class name：com.example.td_horoscope.bean
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/10/28 10:22
 * @class describe
 */
data class IconTitleBean(var icon:Int= R.mipmap.ic_launcher, var title:String="", var hint: String ="", var sex:String="", var birth:String="", var place:String="", override val itemType: Int=1): MultiItemEntity {
}