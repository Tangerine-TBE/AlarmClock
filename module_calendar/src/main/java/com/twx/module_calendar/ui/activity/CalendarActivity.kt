package com.twx.module_calendar.ui.activity


import android.view.Gravity
import android.view.View
import com.twx.module_base.base.BasePopup
import com.twx.module_base.base.BaseVmViewViewActivity
import com.twx.module_base.util.LogUtils
import com.twx.module_base.util.top.toOtherActivity
import com.twx.module_calendar.R
import com.twx.module_calendar.databinding.ActivityCalendarBinding
import com.twx.module_calendar.ui.widget.popup.TimeSelectPopup
import com.twx.module_calendar.viewmodel.CalendarViewModel
import com.twx.module_weather.domain.HuangLiBean
import com.twx.module_weather.ui.activity.HuangLiActivity
import com.twx.module_weather.utils.Constants
import com.google.gson.Gson
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.Calendar.Scheme
import com.haibin.calendarview.CalendarView.OnCalendarSelectListener
import com.haibin.calendarview.CalendarView.OnYearChangeListener
import com.tamsiree.rxkit.RxTimeTool

import java.util.*

class CalendarActivity : BaseVmViewViewActivity<ActivityCalendarBinding, CalendarViewModel>(),
    OnCalendarSelectListener,
    OnYearChangeListener {

    private var mYear=0

    private val mTimePopup by lazy {
        TimeSelectPopup(this)
    }


    override fun getViewModelClass(): Class<CalendarViewModel> {
       return CalendarViewModel::class.java
    }
    override fun getLayoutView(): Int=R.layout.activity_calendar

    override fun initView() {
        binding.apply {
            mYear=calendarView.curYear
            tvYear.text=mYear.toString()
            tvLunar.text="今日"

            val year: Int = calendarView.curYear
            val month: Int = calendarView.curMonth
            val day: Int = calendarView.curDay
            tvMonthDay.text= month.toString() + "月" + day + "日"
            tvCurrentDay.text=day.toString()

            val map: MutableMap<String, Calendar?> = HashMap()
            map[getSchemeCalendar(year, month, 3, -0xbf24db, "假").toString()] =
                getSchemeCalendar(year, month, 3, -0xbf24db, "假")
            map[getSchemeCalendar(year, month, 6, -0x196ec8, "事").toString()] =
                getSchemeCalendar(year, month, 6, -0x196ec8, "事")
            map[getSchemeCalendar(year, month, 9, -0x20ecaa, "议").toString()] =
                getSchemeCalendar(year, month, 9, -0x20ecaa, "议")
            map[getSchemeCalendar(year, month, 13, -0x123a93, "记").toString()] =
                getSchemeCalendar(year, month, 13, -0x123a93, "记")
            map[getSchemeCalendar(year, month, 14, -0x123a93, "记").toString()] =
                getSchemeCalendar(year, month, 14, -0x123a93, "记")
            map[getSchemeCalendar(year, month, 15, -0x5533bc, "假").toString()] =
                getSchemeCalendar(year, month, 15, -0x5533bc, "假")
            map[getSchemeCalendar(year, month, 18, -0x43ec10, "记").toString()] =
                getSchemeCalendar(year, month, 18, -0x43ec10, "记")
            map[getSchemeCalendar(year, month, 25, -0xec5310, "假").toString()] =
                getSchemeCalendar(year, month, 25, -0xec5310, "假")
            map[getSchemeCalendar(year, month, 27, -0xec5310, "多").toString()] =
                getSchemeCalendar(year, month, 27, -0xec5310, "多")
            //此方法在巨大的数据量上不影响遍历性能，推荐使用
           // calendarView.setSchemeDate(map)
            viewModel.getHuangLiMsg(mYear.toString(),month.toString(),day.toString())
        }




    }

    private var huangLiData: HuangLiBean.ResultBean?=null

    override fun observerData() {
        binding.apply {
            viewModel.huangLiInfo.observe(this@CalendarActivity,{
                huangLiData=it
                nongli.text=it.nongli.substring(7)

                val stringBuffer: StringBuffer = getYiJiData(it.yi)
                tvYi.text = "$stringBuffer...查看更多"
                val stringBuffer1: StringBuffer = getYiJiData(it.ji)
                tvJi.text = "$stringBuffer1...查看更多"


                val stringBuffer2 = StringBuffer()
                val suiciStr: List<String> = it.suici
                for (s in suiciStr) {
                    stringBuffer2.append("$s  ")
                }
                suici.text = stringBuffer2

            })
        }


    }

    override fun initEvent() {
        binding.apply {
            calendarView.setOnCalendarSelectListener(this@CalendarActivity)
            calendarView.setOnYearChangeListener(this@CalendarActivity)

            back.setOnClickListener {
                finish()
            }

            huangLiInclude.setOnClickListener {
                toOtherActivity<HuangLiActivity>(this@CalendarActivity) {
                    huangLiData?.let {
                        putExtra(Constants.HUANG_LI_DATA,Gson().toJson(it))
                    }
                }
            }

            tvMonthDay.setOnClickListener {
                if (!calendarLayout.isExpand) {
                    calendarLayout.expand()

                } else {
                    calendarView.showYearSelectLayout(mYear)
                    tvLunar.visibility = View.GONE
                    tvYear.visibility = View.GONE
                    tvMonthDay.text = mYear.toString()
                }


            }

            flCurrent.setOnClickListener {
                calendarView.scrollToCurrent()

            }

            more.setOnClickListener {
                mTimePopup.show(calendarView,Gravity.BOTTOM)
            }


            mTimePopup.setOnActionClickListener(object : BasePopup.OnActionClickListener {
                override fun sure() {
                    java.util.Calendar.getInstance().apply {
                        time=mTimePopup.getCurrentDate()
                        val year = get(java.util.Calendar.YEAR)
                        val month = get(java.util.Calendar.MONTH)+1
                        val day = get(java.util.Calendar.DAY_OF_MONTH)
                        LogUtils.i("==mTimePopup=====$year====$month===$day===${RxTimeTool.date2String(mTimePopup.getCurrentDate())}===")

                        if (calendarView.isYearSelectLayoutVisible) {
                            calendarView.closeYearSelectLayout()
                        }

                        calendarView.scrollToCalendar(year,month,day)


                    }
                }

                override fun cancel() {
                }
            })

        }

    }

    private fun getSchemeCalendar(
        year: Int,
        month: Int,
        day: Int,
        color: Int,
        text: String
    ): Calendar? {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color //如果单独标记颜色、则会使用这个颜色
        calendar.scheme = text
        calendar.addScheme(Scheme())
        calendar.addScheme(-0xff7800, "假")
        calendar.addScheme(-0xff7800, "节")
        return calendar
    }



    override fun onCalendarOutOfRange(calendar: Calendar?) {

    }

    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
        binding.apply {
            tvLunar.visibility = View.VISIBLE
            tvYear.visibility = View.VISIBLE
            tvMonthDay.text = calendar!!.month.toString() + "月" + calendar.day + "日"
            tvYear.text = calendar.year.toString()
            tvLunar.text = calendar.lunar
            mYear = calendar.year
        }
        calendar?.apply {
            viewModel.getHuangLiMsg(year.toString(),month.toString(),day.toString())
            LogUtils.i("==onCalendarSelect=====${year}===$month==$day")
        }

    }

    override fun onYearChange(year: Int) {
       binding. tvMonthDay.text=year.toString()
    }

    private fun getYiJiData(list: List<String>): StringBuffer {
        val stringBuffer = StringBuffer()
        var realList: List<String> = if (list.size >= 9) {
            list.subList(2, 9)
        } else {
            list.subList(0, list.size)
        }
        for (s in realList) {
            stringBuffer.append("$s  ")
        }
        return stringBuffer
    }

    override fun release() {
        mTimePopup.dismiss()
    }
}