package com.twx.clock.ui.activity

import android.view.KeyEvent
import com.twx.module_ad.R
import com.twx.module_base.util.MyStatusBarUtil
import com.twx.td_horoscope.base.MainBaseViewActivity
import com.twx.clock.ui.fragment.AdFragment
import com.twx.clock.ui.fragment.PermissionFragment
import com.twx.module_ad.utils.BaseBackstage
import com.twx.module_ad.utils.Contents
import com.twx.module_base.util.Constants


/**
 * @name td_horoscope
 * @class name：com.example.td_horoscope.ui.activity
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/10 10:50
 * @class describe
 */
class BeginViewActivity: MainBaseViewActivity()  {
    override fun getLayoutView(): Int = R.layout.activity_begin

    private val mAdFragment by lazy {
        AdFragment()
    }
    private val mPermissionFragment by lazy {
        PermissionFragment()
    }


    override fun initView() {
        mSPUtil.putBoolean(Contents.NO_BACK,true)
        //第一次进加载授权页 、后面广告页
        supportFragmentManager.beginTransaction().apply {
            if ( mSPUtil.getBoolean(Constants.IS_FIRST, true))
               add(R.id.mFragmentContainer,mPermissionFragment)
            else add(R.id.mFragmentContainer,mAdFragment)
            commitAllowingStateLoss()
        }

        BaseBackstage.isExit=true
    }

    //全屏
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        MyStatusBarUtil.fullScreenWindow(hasFocus, this)
    }

    //禁用返回键
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean =if (keyCode == KeyEvent.KEYCODE_BACK) true
    else super.onKeyDown(keyCode, event)

}