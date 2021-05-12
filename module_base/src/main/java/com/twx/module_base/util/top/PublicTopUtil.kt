package com.twx.module_base.util.top

import android.content.Intent
import androidx.fragment.app.FragmentActivity

/**
 * @name td_horoscope
 * @class name：com.example.td_horoscope.util.top
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/2 17:54
 * @class describe
 */


inline fun <reified T> toOtherActivity(act: FragmentActivity? ,isFinish:Boolean=false, block: Intent.() -> Unit) {
    val intent = Intent(act, T::class.java)
    intent.block()
    act?.startActivity(intent)
    if (isFinish) {
        act?.finish()
    }
}

inline fun <reified T> toOtherActivityForResult(act: FragmentActivity? ,result:Int, block: Intent.() -> Unit) {
    val intent = Intent(act, T::class.java)
    intent.block()
    act?.startActivityForResult(intent,result)
}






