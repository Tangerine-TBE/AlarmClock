package com.feisukj.base_library.ui

import com.feisukj.base_library.R
import com.feisukj.base_library.baseclass.BaseActivity
import kotlinx.android.synthetic.main.activity_agreement_content_base.*

class AgreementContentActivity:BaseActivity() {
    companion object{
        const val FLAG="agreement_flag"
        const val FLAG_YINSI="yingsi"
        const val FLAG_FUWU="fuwu"
    }
    private var com="深圳市天王星互娱科技有限公司"

    override fun getLayoutId()=R.layout.activity_agreement_content_base

    override fun initView() {
        val flag=intent.getStringExtra(FLAG)
        if (flag==FLAG_FUWU){
            setContentText("服务协议")
            fuwu()
        }else{
            setContentText("隐私政策")
            yinSi()
        }
    }

    override fun initListener() {

    }

    private fun fuwu(){
        agreementContent.text=getString(R.string.userAgreementContent,packageManager.getApplicationLabel(applicationInfo),com)
    }
    private fun yinSi(){
        agreementContent.text=getString(R.string.privacyContent,packageManager.getApplicationLabel(applicationInfo),com)
    }
}