package com.example.td_horoscope.base

import android.content.Context
import com.example.alarmclock.R
import com.example.module_base.base.BaseActivity
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
    override fun getLayoutView(): Int= R.layout.activity_base

    override fun setChildTheme() {
      //  setTheme(if (Math.random()>0.5) R.style.OneTheme else R.style.TwoTheme)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onResume() {
        super.onResume()
  /*      if (!SpUtil.isVIP()) {
            BaseBackstage.setBackstage(this)
        }*/
    }

    override fun onStop() {
        super.onStop()
  /*      if (!SpUtil.isVIP()) {
            BaseBackstage.setStop(this)
        }*/
    }

}