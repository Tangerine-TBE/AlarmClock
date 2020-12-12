package com.example.td_horoscope.base

import android.content.Context
import com.example.alarmclock.R
import com.example.alarmclock.util.DialogUtil
import com.example.module_ad.utils.BaseBackstage
import com.example.module_base.base.BaseActivity
import com.example.module_usercenter.utils.SpUtil
import com.loc.by
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper





/**
 * @name td_horoscope
 * @class name：com.example.td_horoscope.base
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/10 15:30
 * @class describe
 */
open class MainBaseActivity: BaseActivity() {
     val mRemindDialog by lazy { DialogUtil.createRemindDialog(this) }
    private val mJob= Job()
    val mJobScope by lazy {
        CoroutineScope(mJob)
    }

    override fun getLayoutView(): Int= R.layout.activity_base


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onResume() {
        super.onResume()
        if (!SpUtil.isVIP()) {
           // BaseBackstage.setBackstage(this)
        }

    }

    override fun onStop() {
        super.onStop()
        if (!SpUtil.isVIP()) {
        //    BaseBackstage.setStop(this)
        }
    }

    override fun release() {
        super.release()
        mJob?.cancel()
        mRemindDialog.dismiss()
    }


}