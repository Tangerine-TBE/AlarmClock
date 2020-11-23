package com.example.alarmclock.top

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.alarmclock.R
import com.example.alarmclock.ui.weight.BatteryView
import com.example.alarmclock.util.Constants
import com.example.module_base.base.BaseApplication

import com.example.module_base.util.SPUtil

/**
 * @author: 铭少
 * @date: 2020/11/21 0021
 * @description：
 */

fun TextView.setThemeTextColor(){
    setTextColor(getCurrentThemeColor())
    }

private fun View.getCurrentThemeColor():Int {
   return ContextCompat.getColor(
        context, when (SPUtil.getInstance().getInt(Constants.CURRENT_THEME, 0)) {
            0 -> R.color.one_theme_color
            1 -> R.color.two_theme_color
            2 -> R.color.three_theme_color
            3 -> R.color.four_theme_color
            else -> R.color.one_theme_color
        }
    )
}


fun View.setBgResource(){
       setBackgroundResource(when (SPUtil.getInstance().getInt(Constants.CURRENT_THEME,0)) {
               0 -> R.drawable.shape_home_circle_one_bg
               1 ->  R.drawable.shape_home_circle_two_bg
               2 ->  R.drawable.shape_home_circle_three_bg
               3 -> R.drawable.shape_home_circle_four_bg
               else ->  R.drawable.shape_home_circle_one_bg
           }
       )
}

fun BatteryView.setCurrentColor(){
    setColor(getCurrentThemeColor())
}
