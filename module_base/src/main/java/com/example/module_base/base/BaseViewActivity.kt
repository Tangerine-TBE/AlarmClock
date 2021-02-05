package com.example.module_base.base
import android.os.Bundle
import com.example.module_base.util.MyActivityManager
import com.example.module_base.util.MyStatusBarUtil

import com.example.module_base.widget.MyLoadingDialog


/**
 * @name td_horoscope
 * @class name：com.example.module_base.base
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/10/27 19:33
 * @class describe
 */
open abstract class BaseViewActivity :BaseActivity() {


    protected  val mMyLoadingDialog by lazy {
        MyLoadingDialog(this).apply {
            setCancelable(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setChildTheme()
        setContentView(getLayoutView())
        initView()
        initPresent()
        initLoadData()
        initEvent()

    }



    open fun setChildTheme() {
    }



    fun showLoading() {
        if (!isFinishing) {
            mMyLoadingDialog.show()
        }
    }

    fun dismissLoading() {
        mMyLoadingDialog.dismiss()
    }




    abstract fun getLayoutView(): Int


    open fun initEvent() {

    }

    open fun initLoadData() {

    }

    open fun initPresent() {

    }

    open fun initView() {

    }



}