package com.example.alarmclock.topfun

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.alarmclock.R
import com.example.alarmclock.ui.widget.BatteryView
import com.example.alarmclock.util.Constants

import com.example.module_base.util.SPUtil

/**
 * @author: 铭少
 * @date: 2020/11/21 0021
 * @description：
 */

fun TextView.setThemeTextColor() {
    setTextColor(setCurrentThemeColor(context))
}


 fun setCurrentThemeColor(context: Context):Int{
     return ContextCompat.getColor(
             context, when (SPUtil.getInstance().getInt(Constants.CURRENT_THEME, 0)) {
         Constants.THEME_ONE -> R.color.skin_number_one
         Constants.THEME_TWO -> R.color.skin_number_two
         Constants.THEME_THREE -> R.color.skin_number_three
         Constants.THEME_FOUR -> R.color.skin_number_four
         Constants.THEME_FIVE -> R.color.skin_number_five
         Constants.THEME_SIX -> R.color.skin_number_six
         Constants.THEME_SEVEN -> R.color.skin_number_seven
         Constants.THEME_EIGHT -> R.color.skin_number_eight
         Constants.THEME_NINE -> R.color.skin_watch_one
         Constants.THEME_TEN -> R.color.skin_watch_two
         else -> R.color.skin_number_one
     }
     )
 }



fun BatteryView.setCurrentColor() {
    setColor(setCurrentThemeColor(context))
}




fun ImageView.setTintImage() {
    setColorFilter(setCurrentThemeColor(context))
}


fun textViewColorTheme(vararg textView: TextView) {
    textView.forEach {
        it.setThemeTextColor()
    }

}
