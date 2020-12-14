package com.example.alarmclock.util

import android.graphics.Typeface
import com.example.module_base.base.BaseApplication

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.util
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/12/14 17:37:51
 * @class describe
 */
object TextViewType {

    fun getTextViewType(): Typeface?{
        try {
          return  Typeface.createFromAsset(BaseApplication.getContext().assets, "fonts/digital-7.TTF");
        }catch (e:Exception){
        }
        return null
    }

}