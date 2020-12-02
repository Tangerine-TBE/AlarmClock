package com.example.alarmclock.topfun

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.alarmclock.R
import com.example.alarmclock.ui.weight.BatteryView
import com.example.alarmclock.util.Constants

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
       Constants.THEME_ONE-> R.color.one_theme_color
       Constants.THEME_TWO-> R.color.two_theme_color
       Constants.THEME_THREE -> R.color.three_theme_color
       Constants.THEME_FOUR -> R.color.four_theme_color
            else -> R.color.one_theme_color
        }
    )
}


fun View.setBgResource(){
       setBackgroundResource(when (SPUtil.getInstance().getInt(Constants.CURRENT_THEME,0)) {
                Constants.THEME_ONE -> R.drawable.shape_home_circle_one_bg
                Constants.THEME_TWO ->  R.drawable.shape_home_circle_two_bg
                Constants.THEME_THREE  ->  R.drawable.shape_home_circle_three_bg
                Constants.THEME_FOUR  -> R.drawable.shape_home_circle_four_bg
               else ->  R.drawable.shape_home_circle_one_bg
           }
       )
}

fun BatteryView.setCurrentColor(){
    setColor(getCurrentThemeColor())
}


fun textViewColorTheme(vararg textView:TextView){
    textView.forEach {
        it.setThemeTextColor()
    }

    fun viewBackgroundTheme(vararg view:View){
        view.forEach {
            it.setBgResource()
        }
    }


}

