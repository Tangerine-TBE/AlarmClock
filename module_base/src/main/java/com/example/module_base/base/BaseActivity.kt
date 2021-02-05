package com.example.module_base.base

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.module_base.util.MyActivityManager
import com.example.module_base.util.MyStatusBarUtil
import com.example.module_base.util.SPUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 * @author: 铭少
 * @date: 2021/1/16 0016
 * @description：
 */
open class BaseActivity:FragmentActivity() {

    protected val mJob=Job()
    protected val mJobScope by lazy {
        CoroutineScope(mJob)
    }
    protected lateinit var mSPUtil: SPUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityFullWindow()
        mSPUtil = SPUtil.getInstance()
        MyActivityManager.addActivity(this)
    }

    open fun setActivityFullWindow(){
        MyStatusBarUtil.setFullWindow(this)
    }

    fun visible(vararg views: View) {
        for (view in views) {
            view.visibility = View.VISIBLE
        }
    }

    fun invisible(vararg views: View){
        for (view in views) {
            view.visibility = View.INVISIBLE
        }
    }


    fun gone(vararg views: View) {
        for (view in views) {
            view.visibility = View.GONE
        }
    }
    open fun release() {

    }

    override fun onDestroy() {
        super.onDestroy()
        MyActivityManager.removeActivity(this)
        release()

    }
}