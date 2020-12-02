package com.example.module_base.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.module_base.util.MyActivityManager
import com.example.module_base.util.MyStatusBarUtil
import com.example.module_base.util.SPUtil
import com.example.module_base.widget.LoadingDialog


/**
 * @name td_horoscope
 * @class name：com.example.module_base.base
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/10/27 19:33
 * @class describe
 */
open abstract class BaseActivity : FragmentActivity() {
    protected lateinit var mSPUtil: SPUtil
    protected lateinit var mLoadingDialog //正在加载
            : LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSPUtil = SPUtil.getInstance();
        setActivityFullWindow()
        setChildTheme()
        setContentView(getLayoutView())
        MyActivityManager.addActivity(this)
        mLoadingDialog = LoadingDialog(this)
        mLoadingDialog.setCancelable(true)
        initView()
        initPresent()
        initLoadData()
        initEvent()

    }

    open fun setChildTheme() {
    }

    open fun setActivityFullWindow(){
     MyStatusBarUtil.setFullWindow(this)
 }


    fun showLoading() {
        if (!mLoadingDialog.isShowing) {
            mLoadingDialog.show()
        }
    }

    fun dismissLoading() {
        mLoadingDialog.dismiss()
    }

    fun visible(vararg views: View) {
        for (view in views) {
            view.visibility = View.VISIBLE
        }
    }

    fun invisible(vararg views: View) {
        for (view in views) {
            view.visibility = View.INVISIBLE
        }
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

    open fun release() {

    }

    override fun onDestroy() {
        super.onDestroy()
        MyActivityManager.removeActivity(this)
        release()
    }


}