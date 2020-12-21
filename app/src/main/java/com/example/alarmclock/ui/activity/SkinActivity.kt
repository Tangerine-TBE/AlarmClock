package com.example.alarmclock.ui.activity

import androidx.recyclerview.widget.GridLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.model.DataProvider
import com.example.alarmclock.ui.adapter.recyclerview.SkinAdapter
import com.example.alarmclock.util.Constants
import com.example.module_base.util.MarginStatusBarUtil
import com.example.module_base.util.top.toOtherActivity
import com.example.module_base.widget.MyToolbar
import com.example.module_usercenter.ui.activity.BuyVipActivity
import com.example.module_usercenter.utils.Contents
import com.example.module_usercenter.utils.SpUtil
import com.example.td_horoscope.base.MainBaseActivity
import kotlinx.android.synthetic.main.activity_skin.*

class SkinActivity : MainBaseActivity() {
    private lateinit var mSkinAdapter: SkinAdapter
    override fun getLayoutView(): Int=R.layout.activity_skin


    override fun initView() {
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mSkinBar, 1)
        mSkinContainer.layoutManager = GridLayoutManager(this, 2)
        mSkinAdapter= SkinAdapter()
        mSkinAdapter.setList(DataProvider.skinData)
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
            if (DataProvider.skinData[position].isOpen) {
                if (SpUtil.isVIP()) {
                    changeSkin(position)
                } else {
                    toOtherActivity<BuyVipActivity>(this, false) {putExtra(Contents.TO_BUY,true)}
                }
            } else {
                changeSkin(position)
            }

        }
    }

    private fun changeSkin(position: Int) {
        mSkinAdapter.setCurrentPosition(position)
        mSPUtil.putInt(Constants.CURRENT_THEME, position);
        finish()
    }


}