package com.example.alarmclock.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alarmclock.R
import com.example.alarmclock.util.MarginStatusBarUtil
import com.example.module_base.base.BaseActivity
import com.example.module_base.widget.MyToolbar
import com.example.td_horoscope.base.MainBaseActivity
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_skin.*
import kotlinx.android.synthetic.main.activity_tell_time.*

class SkinActivity : MainBaseActivity() {
    override fun getLayoutView(): Int=R.layout.activity_skin

    override fun initView() {
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mSkinBar, 1)

    }
    override fun initEvent() {
        mSkinBar.setOnBackClickListener(object : MyToolbar.OnBackClickListener{
            override fun onBack() {
                finish()
            }
            override fun onRightTo() {

            }
        })
    }


}