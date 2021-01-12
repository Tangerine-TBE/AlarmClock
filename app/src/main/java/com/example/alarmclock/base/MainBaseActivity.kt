package com.example.td_horoscope.base

import android.content.Context
import com.example.alarmclock.R
import com.example.alarmclock.util.DialogUtil
import com.example.module_ad.advertisement.AdType
import com.example.module_ad.utils.BaseBackstage
import com.example.module_base.base.BaseActivity
import com.example.module_usercenter.utils.SpUtil
import com.loc.by
import com.tamsiree.rxkit.RxDeviceTool
import com.tamsiree.rxkit.RxTimeTool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel





/**
 * @name td_horoscope
 * @class name：com.example.td_horoscope.base
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/10 15:30
 * @class describe
 */
open class MainBaseActivity: BaseActivity() {
    protected var isShow=true
    private val mJob= Job()
    val mJobScope by lazy {
        CoroutineScope(mJob)
    }

    override fun getLayoutView(): Int= R.layout.activity_base


    override fun onResume() {
        super.onResume()
        if (!SpUtil.isVIP()) {
           BaseBackstage.setBackstage(this)
        }

    }

    override fun onStop() {
        super.onStop()
        if (!SpUtil.isVIP()) {
            BaseBackstage.setStop(this)
        }
    }


}