package com.example.alarmclock.topfun

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.alarmclock.bean.ItemBean
import com.example.alarmclock.bean.SkinType
import com.example.alarmclock.repository.DataProvider
import com.example.alarmclock.ui.widget.BatteryView
import com.example.alarmclock.ui.widget.skin.calendar.TabDigit
import com.example.alarmclock.util.Constants
import com.example.module_base.util.SPUtil

import com.example.module_base.util.gsonHelper
import com.tamsiree.rxkit.RxDeviceTool

/**
 * @author: 铭少
 * @date: 2020/11/21 0021
 * @description：
 */




fun  setRadiusBg(vararg view:RelativeLayout,block:RelativeLayout.()->Unit){
        view.forEach {
            it.block()
        }
}


fun TextView.setThemeTextColor() {
    setTextColor(ContextCompat.getColor(context,setCurrentThemeColor().bgcolor))
}


 fun setCurrentThemeColor():ItemBean{
     val skinTypeStr = SPUtil.getInstance().getString(Constants.CURRENT_THEME)
     val skinType = gsonHelper<SkinType>(skinTypeStr)
     return  skinType?.skin?: DataProvider.skinNumber[0]
 }



fun BatteryView.setCurrentColor() {
    setColor(ContextCompat.getColor(context,setCurrentThemeColor().bgcolor))
}


fun tabDigitViewColorTheme(vararg tabDigit: TabDigit) {
    tabDigit.forEach {
        it.backgroundColor =ContextCompat.getColor(it.context,setCurrentThemeColor().bgcolor)
        it.textColor =ContextCompat.getColor(it.context,setCurrentThemeColor().titlecolor)
    }
}


fun ImageView.setTintImage() {
    setColorFilter(ContextCompat.getColor(context,setCurrentThemeColor().bgcolor))
}




fun textViewColorTheme(vararg textView: TextView) {
    textView.forEach {
        it.setThemeTextColor()
    }
}

fun textViewLandSize(size:Float, context: Context, vararg textView: TextView){
    if (RxDeviceTool.isLandscape(context)) {
        if (RxDeviceTool.getScreenWidth(context)<1920) {
            textView.forEach {
                it.textSize=size
            }
        }
    }
}


fun Dialog.showDialog(activity: Activity) {
    if (!activity.isFinishing) {
        show()
    }
}


inline fun orientationAction(context: Context,portrait:()->Unit,landscape:()->Unit){
    if (RxDeviceTool.isPortrait(context)) {
        portrait()
    } else {
        landscape()
    }
}
