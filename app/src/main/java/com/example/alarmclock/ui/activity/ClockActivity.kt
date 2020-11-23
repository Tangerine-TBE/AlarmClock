package com.example.alarmclock.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alarmclock.R
import com.example.alarmclock.util.MarginStatusBarUtil
import com.example.module_base.base.BaseActivity
import com.example.module_base.util.top.toOtherActivity
import com.example.module_base.widget.MyToolbar
import com.example.td_horoscope.base.MainBaseActivity
import kotlinx.android.synthetic.main.activity_clock.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_tell_time.*

class ClockActivity : MainBaseActivity() {
    override fun getLayoutView(): Int=R.layout.activity_clock
    override fun initView() {
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mClockBar, 1)
    }

    override fun initEvent() {
        mClockBar.setOnBackClickListener(object : MyToolbar.OnBackClickListener{
            override fun onBack() {
                finish()
            }
            override fun onRightTo() {

            }
        })

        mSetClock.setOnClickListener {
            toOtherActivity<AddClockActivity>(this,false){}
        }


    }
}