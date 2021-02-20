package com.feisukj.base_library.ui

import android.content.pm.PackageManager
import com.feisukj.base_library.R
import com.feisukj.base_library.baseclass.BaseActivity
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity: BaseActivity() {
    override fun initListener() {

    }
    override fun initView() {
        setContentText(R.string.aboutUS)
        val info=packageManager.getPackageInfo(packageName,PackageManager.GET_META_DATA)
        version.text = info.versionName
        appName.text=applicationInfo.loadLabel(packageManager)
        iv_icon.setImageDrawable(applicationInfo.loadIcon(packageManager))
    }
    override fun getLayoutId()=R.layout.activity_about
}