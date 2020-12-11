package com.example.alarmclock.ui.fragment

import com.example.alarmclock.R
import com.example.alarmclock.ui.activity.MainActivity
import com.example.module_ad.advertisement.SplashHelper
import com.example.module_ad.bean.AdBean
import com.example.module_ad.request.AdPresent
import com.example.module_ad.request.IAdCallback
import com.example.module_ad.utils.AdMsgUtil
import com.example.module_ad.utils.Contents
import com.example.module_base.base.BaseFragment
import com.example.module_base.util.top.toOtherActivity
import com.google.gson.Gson
import com.tamsiree.rxkit.RxNetTool
import kotlinx.android.synthetic.main.fragment_ad.*

/**
 * @name td_horoscope
 * @class name：com.example.td_horoscope.ui.fragment
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/10 10:53
 * @class describe
 */
class AdFragment:BaseFragment(), IAdCallback {

    private var isShow=false
    override fun getLayoutView(): Int= R.layout.fragment_ad
    private val madPresent by lazy {
        AdPresent.getInstance()
    }

    private lateinit var mSplashHelper:SplashHelper

    override fun initView() {
        switchUIByState(PageState.SUCCESS)
        mSplashHelper= SplashHelper(activity, mSplashContainer, MainActivity::class.java)
    }

    override fun initPresent() {
        madPresent.registerCallback(this)

        if (RxNetTool.isNetworkAvailable(mActivity)) {
            if (AdMsgUtil.getADKey() != null) {
                mSplashHelper.showAd()
                isShow=true
            }
            madPresent.getAdMsg()

        } else {
           toOtherActivity<MainActivity>(activity,true){}
        }
    }

    override fun onLoadAdSuccess(adBean: AdBean?) {
        adBean?.let {
            mSPUtil.putString(Contents.AD_INFO,Gson().toJson(it.data))
            if (!isShow) {
                mSplashHelper.showAd()
            }
        }

    }

    override fun onLoadError() {
       toOtherActivity<MainActivity>(activity,true){}
    }


    override fun release() {
        madPresent.unregisterCallback(this)
    }

}