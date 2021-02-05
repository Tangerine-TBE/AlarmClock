package com.example.module_base.util

import com.google.gson.Gson

/**
 * @name AlarmClock
 * @class name：com.example.module_base.util
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/5 11:38:05
 * @class describe
 */


inline fun <reified T> gsonHelper(result: String?): T? =
        try {
            if (result!=null) {
                Gson().fromJson(result, T::class.java)
            } else {
                null
            }
        }catch (e: Exception){
            null
        }