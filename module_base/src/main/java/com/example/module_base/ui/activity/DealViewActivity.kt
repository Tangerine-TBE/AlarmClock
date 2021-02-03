package com.example.module_base.ui.activity

import com.example.module_base.R
import com.example.module_base.base.BaseViewActivity
import com.example.module_base.util.Constants
import com.example.module_base.util.PackageUtil
import com.example.module_base.widget.MyToolbar
import kotlinx.android.synthetic.main.activity_deal.*

class DealViewActivity : BaseViewActivity()  {


    var mTitleMsg="用户协议"
    var mContent=R.string.user


    override fun setActivityFullWindow() {

    }
    override fun getLayoutView(): Int = R.layout.activity_deal
    override fun initView() {
        when (intent.getIntExtra(Constants.SET_Deal1, 0)) {
            2 -> {
                mTitleMsg="用户协议"
                mContent=R.string.user
            }
            3 -> {
                mTitleMsg="隐私协议"
                mContent=R.string.privacy
            }
        }
        privacy_toolbar.setTitle(mTitleMsg)
        text.text = PackageUtil.difPlatformName(this,mContent)
    }

    override fun initEvent() {
        privacy_toolbar.setOnBackClickListener(object :MyToolbar.OnBackClickListener{
            override fun onBack() {
               finish()
            }

            override fun onRightTo() {

            }
        })
    }

}