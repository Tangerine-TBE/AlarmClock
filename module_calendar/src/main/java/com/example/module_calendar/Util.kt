package com.example.module_calendar

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import java.util.*

/**
 * @name AlarmClock
 * @class name：com.example.module_calendar
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/22 13:45:20
 * @class describe
 */


fun showTimePicker(
        context: Context,
        selectTime: Calendar,
        container: ViewGroup,
        onTimeChange: (Date) -> Unit
) :TimePickerView{

    /**
     * @description
     *
     * 注意事项：
     * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
     * 具体可参考demo 里面的两个自定义layout布局。
     * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
     * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
     */
    // val selectedDate = Calendar.getInstance() //系统当前时间
    //时间选择器 ，自定义布局
  return  TimePickerBuilder(context, OnTimeSelectListener { date, v -> })
            /*.setType(TimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
               / *.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)
                 .setRangDate(startDate, endDate)*/
            /*.animGravity(Gravity.RIGHT)// default is center*/
            .isCyclic(true)
            .setDate(selectTime)
             .setRangDate( Calendar.getInstance().apply { set(1901,0,1) },  Calendar.getInstance().apply { set(2099,1,1) })
            .setLayoutRes(R.layout.pickerview_custom_time) { v -> }
            .setTimeSelectChangeListener {
                onTimeChange(it)
            }
            .setContentTextSize(25)
            .setTextColorCenter(ContextCompat.getColor(context, R.color.themeColor))//设置选中项的颜色
            .setTextColorOut(Color.parseColor("#858585"))
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .setLabel("年", "月", "日", "时", "分", "秒")
            .setLineSpacingMultiplier(2.0f)
            .setTextXOffset(0, 0, 0, 0, 0, 0)
            .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            .setDividerColor(ContextCompat.getColor(context, R.color.transparent))
            .setItemVisibleCount(5)
            .setDecorView(container)
            .setOutSideCancelable(false)
            .build().apply {
                setKeyBackCancelable(false)
                show(false)
            }




}

fun getWeekOfDate(date: Date): String {
        val weekDays = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
        val cal = Calendar.getInstance()
        cal.time = date
        var w = cal[Calendar.DAY_OF_WEEK] - 1
        if (w < 0) w = 0
        return weekDays[w]
}