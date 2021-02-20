package com.example.module_calendar.ui.activity


import android.view.View
import com.example.module_base.base.BaseVmViewViewActivity
import com.example.module_calendar.R
import com.example.module_calendar.databinding.ActivityCalendarBinding
import com.example.module_calendar.viewmodel.CalendarViewModel
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.Calendar.Scheme
import com.haibin.calendarview.CalendarView.OnCalendarSelectListener
import com.haibin.calendarview.CalendarView.OnYearChangeListener
import java.util.*

class CalendarActivity : BaseVmViewViewActivity<ActivityCalendarBinding, CalendarViewModel>(),
    OnCalendarSelectListener,
    OnYearChangeListener {

    private var mYear=0

    override fun getViewModelClass(): Class<CalendarViewModel> {
       return CalendarViewModel::class.java
    }
    override fun getLayoutView(): Int=R.layout.activity_calendar

    override fun initView() {
        binding.apply {
            mYear=calendarView.curYear

            tvMonthDay.text= calendarView.curMonth.toString() + "月" + calendarView.curDay + "日"

            tvLunar.text="今日"

            tvCurrentDay.text=calendarView.curDay.toString()


            val year: Int = calendarView.curYear
            val month: Int = calendarView.curMonth

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
            calendarView.setSchemeDate(map)
        }

    }

    override fun initEvent() {
        binding.apply {
            calendarView.setOnCalendarSelectListener(this@CalendarActivity)
            calendarView.setOnYearChangeListener(this@CalendarActivity)


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


    }

    override fun onYearChange(year: Int) {
       binding. tvMonthDay.text=year.toString()
    }
}