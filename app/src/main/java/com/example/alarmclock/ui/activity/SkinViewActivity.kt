package com.example.alarmclock.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.model.DataProvider
import com.example.alarmclock.ui.adapter.recyclerview.SkinAdapter
import com.example.alarmclock.ui.adapter.viewpager.SkinViewPagerAdapter
import com.example.alarmclock.util.Constants
import com.example.module_ad.advertisement.AdType
import com.example.module_ad.advertisement.InsertHelper
import com.example.module_ad.service.TimeService
import com.example.module_base.util.MarginStatusBarUtil
import com.example.module_base.util.top.toOtherActivity
import com.example.module_base.widget.MyToolbar
import com.example.module_usercenter.ui.activity.BuyVipViewActivity
import com.example.module_usercenter.utils.Contents
import com.example.module_usercenter.utils.SpUtil
import com.example.td_horoscope.base.MainBaseViewActivity
import kotlinx.android.synthetic.main.activity_skin.*

class SkinViewActivity : MainBaseViewActivity() {
    private lateinit var mSkinAdapter: SkinAdapter
    private val mInsertHelper by lazy {
        InsertHelper(this)
    }

    private val mAdapter by lazy {
        SkinViewPagerAdapter(supportFragmentManager)
    }

    private val serviceConnection by lazy {
        object:ServiceConnection{
            override fun onServiceDisconnected(name: ComponentName?) {
            }
            override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                val myBinder = service as? TimeService.MyBinder
                myBinder?.let { it ->
                    it.getService.showSkin.observe(this@SkinViewActivity, Observer {
                        isShow=it

                    })
                    if (isShow) {
                        mInsertHelper.showAd(AdType.SKIN_PAGE)
                    }
                }
            }
        }
    }


    override fun getLayoutView(): Int=R.layout.activity_skin
    override fun initView() {
        //设置顶部距离
       MarginStatusBarUtil.setStatusBar(this, mSkinBar, 2)
      //  mSkinContainer.layoutManager = GridLayoutManager(this, 2)
        mSkinAdapter= SkinAdapter()
        mSkinAdapter.setList(DataProvider.skinData)
    //    mSkinContainer.adapter=mSkinAdapter
        bindService(Intent(this,TimeService::class.java),serviceConnection,Context.BIND_AUTO_CREATE)

        tabLayout.setupWithViewPager(skinViewPager)
        skinViewPager.adapter=mAdapter

    }
    override fun initEvent() {
        iv_bar_back.setOnClickListener {
            finish()
        }

        mSkinAdapter.setOnItemClickListener { adapter, view, position ->
            if (DataProvider.skinData[position].isOpen) {
                if (SpUtil.isVIP()) {
                    changeSkin(position)
                } else {
                    toOtherActivity<BuyVipViewActivity>(this, false) {putExtra(Contents.TO_BUY,true)}
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

    override fun onPause() {
        super.onPause()
        mInsertHelper.releaseAd()
    }

    override fun release() {
        super.release()
        mInsertHelper.releaseAd()
        unbindService(serviceConnection)
    }

}