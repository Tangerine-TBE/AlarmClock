package com.twx.clock.ui.fragment

import com.twx.clock.R
import com.twx.clock.databinding.FragmentAdBinding
import com.twx.clock.ui.activity.MainViewActivity
import com.twx.module_ad.advertisement.SplashHelper
import com.twx.module_ad.bean.AdBean
import com.twx.module_ad.request.AdPresent
import com.twx.module_ad.request.IAdCallback
import com.twx.module_ad.utils.AdMsgUtil
import com.twx.module_ad.utils.Contents
import com.twx.module_base.base.BaseViewFragment
import com.twx.module_base.util.top.toOtherActivity
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
class AdFragment: BaseViewFragment<FragmentAdBinding>(), IAdCallback {

    private var isShow=false
    override fun getChildLayout(): Int =R.layout.fragment_ad

    private val madPresent by lazy {
        AdPresent.getInstance()
    }

    private lateinit var mSplashHelper:SplashHelper

    override fun initView() {
        mSplashHelper= SplashHelper(activity, mSplashContainer, MainViewActivity::class.java)


        madPresent.registerCallback(this)

        if (RxNetTool.isNetworkAvailable(requireContext())) {
            if (AdMsgUtil.getADKey() != null) {
                mSplashHelper.showAd()
                isShow=true
            }
            madPresent.getAdMsg()

        } else {
            toOtherActivity<MainViewActivity>(activity,true){}
        }

    }



    override fun onLoadAdSuccess(adBean: AdBean?) {
        adBean?.let {
            sp.putString(Contents.AD_INFO,Gson().toJson(it.data))
            if (!isShow) {
                mSplashHelper.showAd()
            }
        }

    }

    override fun onLoadError() {
       toOtherActivity<MainViewActivity>(activity,true){}
    }


    override fun release() {
        madPresent.unregisterCallback(this)
    }



}