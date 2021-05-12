package com.twx.module_weather.livedata

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.livedata
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/19 16:41:53
 * @class describe
 */
object DrawableLiveData:LiveData<Drawable>() {


    fun setTopDrawable(draw:Drawable){
        value=draw
    }

}