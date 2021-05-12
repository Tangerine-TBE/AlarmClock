package com.twx.module_calendar.ui.widget.popup

import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.twx.module_base.base.BasePopup
import com.twx.module_calendar.R
import com.twx.module_calendar.getWeekOfDate
import com.twx.module_calendar.showTimePicker
import com.tamsiree.rxkit.RxTimeTool
import kotlinx.android.synthetic.main.popup_time_select.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * @name AlarmClock
 * @class name：com.example.module_calendar.ui.widget.popup
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/22 13:52:09
 * @class describe
 */
class TimeSelectPopup(activity: FragmentActivity):BasePopup(activity, R.layout.popup_time_select,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT) {

    private var mDate=Date()

    private var isLunar=true

    init {
        view?.apply {
            showCurrentTime(Date())
            val timePicker = showTimePicker(activity, Calendar.getInstance(), timeGroup) {
                showCurrentTime(it)
                mDate = it
            }
            cancelText.setOnClickListener {
                this@TimeSelectPopup.dismiss()
            }

            sureText.setOnClickListener {
                mListener?.sure()
                this@TimeSelectPopup.dismiss()
            }


            changedType.setOnClickListener {
                timePicker.isLunarCalendar=isLunar
                isLunar=!isLunar
                changedType.text=if (isLunar) "查看农历" else "查看公历"
            }
        }

    }


    private fun showCurrentTime(data:Date){
        view.currentTime.text=RxTimeTool.date2String(data, SimpleDateFormat("yyyy年MM月dd日"))+"    ${getWeekOfDate(data)}"
    }

    fun getCurrentDate()=mDate

}