package com.example.module_weather.ui.activity

import com.example.module_base.base.BaseViewActivity
import com.example.module_base.util.DateUtil
import com.example.module_base.util.MarginStatusBarUtil
import com.example.module_base.util.gsonHelper
import com.example.module_base.util.setBarAction
import com.example.module_weather.R
import com.example.module_weather.domain.HuangLiBean
import com.example.module_weather.domain.ValueHuangLiData
import com.example.module_weather.utils.Constants
import kotlinx.android.synthetic.main.activity_huangli.*

class HuangLiActivity : BaseViewActivity() {

    override fun getLayoutView(): Int = R.layout.activity_huangli
    override fun initView() {
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, hl_toolbar, 0)

        val result =  intent.getStringExtra(Constants.HUANG_LI_DATA)

        gsonHelper<HuangLiBean.ResultBean>(result)?.let { resultBean ->
            tv_date.text = "${resultBean.year}年${resultBean.month}月${resultBean.day}日"
            tv_nongli.text = resultBean.nongli.substring(7)
            tv_year_xx.text = resultBean.shengxiao.toString() + "年"
            tv_week_xx.text = "星期" + resultBean.week
            tv_big_date.text = resultBean.day


            // 宜
            val stringBuffer1 = StringBuffer()
            var yi: List<String> = if (resultBean.yi.size >= 9) {
                resultBean.yi.subList(2, 9)
            } else {
                resultBean.yi.subList(0, resultBean.yi.size)
            }
            for (s in yi) {
                stringBuffer1.append("$s  ")
            }
            tv_yi_hl_text.text = stringBuffer1
            //忌
            val stringBuffer2 = StringBuffer()
            var ji: List<String> = if (resultBean.ji.size >= 9) {
                resultBean.ji.subList(2, 9)
            } else {
                resultBean.ji.subList(0, resultBean.ji.size)
            }
            for (s in ji) {
                stringBuffer2.append("$s  ")
            }
            tv_ji_hl_text.text = stringBuffer2

            tv_xx_hl_text.text = resultBean.shengxiao
            tv_xz_hl_text.text = resultBean.star
            tv_wx_hl_text.text = resultBean.wuxing

            tv_cs_hl_text.text = """
                喜神：${resultBean.xishen}
                福神：${resultBean.fushen}
                财神：${resultBean.caishen}
                
                """.trimIndent()

            tv_ts_hl_text.text = resultBean.taishen

            tv_c_hl_text.text = resultBean.chong
            tv_s_hl_text.text = resultBean.sha

            tv_xiongshen.text = resultBean.xiongshen
            tv_jishenyiqu.text = resultBean.jishenyiqu

            val stringBuffer = StringBuffer()
            val suici: List<String> = resultBean.suici
            for (s in suici) {
                stringBuffer.append("$s  ")
            }
            tv_suici.text = stringBuffer
            tv_jiri.text = resultBean.jiri

        }
    }

    override fun initEvent() {
        hl_toolbar.setBarAction(this){}
    }

}