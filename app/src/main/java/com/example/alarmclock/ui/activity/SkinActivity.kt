package com.example.alarmclock.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.model.DataProvider
import com.example.alarmclock.ui.adapter.recyclerview.SkinAdapter
import com.example.alarmclock.util.Constants
import com.example.alarmclock.util.MarginStatusBarUtil
import com.example.module_base.base.BaseActivity
import com.example.module_base.widget.MyToolbar
import com.example.td_horoscope.base.MainBaseActivity
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_skin.*
import kotlinx.android.synthetic.main.activity_tell_time.*

class SkinActivity : MainBaseActivity() {
    private lateinit var mSkinAdapter: SkinAdapter
    override fun getLayoutView(): Int=R.layout.activity_skin
    override fun initView() {
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mSkinBar, 1)
        mSkinContainer.layoutManager=GridLayoutManager(this,2)
        mSkinAdapter= SkinAdapter()
        mSkinAdapter.setList(DataProvider.viewData)
        mSkinContainer.adapter=mSkinAdapter
    }
    override fun initEvent() {
        mSkinBar.setOnBackClickListener(object : MyToolbar.OnBackClickListener{
            override fun onBack() {
                finish()
            }
            override fun onRightTo() {

            }
        })

        mSkinAdapter.setOnItemClickListener { adapter, view, position ->
            val currentTheme = when (position) {
                0 -> 0
                1 -> 1
                2 -> 2
                3 -> 3
                else->1
            }
            mSPUtil.putInt(Constants.CURRENT_THEME,currentTheme);
            finish()
        }
    }


}