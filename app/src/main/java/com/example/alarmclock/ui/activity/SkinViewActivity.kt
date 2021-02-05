package com.example.alarmclock.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.Observer
import com.example.alarmclock.R
import com.example.alarmclock.repository.DataProvider
import com.example.alarmclock.ui.adapter.recyclerview.SkinAdapter
import com.example.alarmclock.ui.adapter.viewpager.SkinViewPagerAdapter
import com.example.module_ad.advertisement.AdType
import com.example.module_ad.advertisement.InsertHelper
import com.example.module_ad.service.TimeService
import com.example.module_base.util.MarginStatusBarUtil
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
        mSkinAdapter.setList(DataProvider.skinNumber)
    //    mSkinContainer.adapter=mSkinAdapter
        bindService(Intent(this,TimeService::class.java),serviceConnection,Context.BIND_AUTO_CREATE)

        tabLayout.setupWithViewPager(skinViewPager)
        skinViewPager.adapter=mAdapter
        skinViewPager.offscreenPageLimit=3
    }
    override fun initEvent() {
        iv_bar_back.setOnClickListener {
            finish()
        }


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