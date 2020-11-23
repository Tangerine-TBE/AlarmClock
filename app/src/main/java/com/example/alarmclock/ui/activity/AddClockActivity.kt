package com.example.alarmclock.ui.activity

import com.example.alarmclock.R
import com.example.alarmclock.ui.weight.ClockSelectView
import com.example.alarmclock.util.MarginStatusBarUtil
import com.example.module_base.util.LogUtils
import com.example.module_base.widget.MyToolbar
import com.example.td_horoscope.base.MainBaseActivity
import kotlinx.android.synthetic.main.activity_add_clock.*
import java.util.*


class AddClockActivity : MainBaseActivity() {

    override fun getLayoutView(): Int=R.layout.activity_add_clock
    private val mClockSelectView by lazy {
         ClockSelectView()
    }

    override fun initView() {
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mAddClock, 1)

    }

    override fun initEvent() {
        mAddClock.setOnBackClickListener(object :MyToolbar.OnBackClickListener{
            override fun onBack() {
                finish()
            }
            override fun onRightTo() {

            }
        })

        mClockSelectView.showTimePicker(this,mTimeSelect) {
            LogUtils.i("--*--mTimeSelect-------${it.time}--------------")
        }

    }






}