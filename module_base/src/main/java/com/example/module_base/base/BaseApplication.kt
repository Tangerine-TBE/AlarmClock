package com.example.module_base.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Handler
import com.alibaba.android.arouter.launcher.ARouter
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI
import com.example.module_base.provider.ModuleProvider
import com.example.module_base.util.PackageUtil
import com.example.module_base.util.SPUtil
import com.tamsiree.rxkit.RxTool
import com.tamsiree.rxkit.crash.TCrashTool
import com.umeng.commonsdk.UMConfigure
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.litepal.LitePal

/**
 * @name td_horoscope
 * @class name：com.example.module_base.base
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/10/27 19:34
 * @class describe
 */
 open class BaseApplication:Application() {

    companion object {
       lateinit var application:BaseApplication
        var appContext: Context? = null
        fun getContext(): Context {
            return appContext!!
        }
        var mMainHandler:Handler?=null
        fun getMainHandler(): Handler {
            return mMainHandler!!
        }
        lateinit var packName:String

    }


    @SuppressLint("RestrictedApi")
    override fun onCreate() {
        super.onCreate()
        application=this
        appContext = baseContext
        mMainHandler= Handler()
        packName=packageName


        RxTool.init(this@BaseApplication)
        TCrashTool.getConfig().setEnabled(false)
        GlobalScope.launch {
            SPUtil.init(this@BaseApplication)
            LitePal.initialize(this@BaseApplication)
            LitePal.getDatabase()
            ARouter.init(this@BaseApplication)
            // ARouter.openDebug();
            //用户反馈
            FeedbackAPI.init(this@BaseApplication, "25822454", "7a8bb94331a5141dcea61ecb1056bbbd")
            val jsonObject = JSONObject()
            try {
                jsonObject.put("appId", PackageUtil.getAppProcessName(this@BaseApplication))
                jsonObject.put("appName",PackageUtil.getAppMetaData(this@BaseApplication,ModuleProvider.APP_NAME))
                jsonObject.put("ver", PackageUtil.packageCode2(applicationContext))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            FeedbackAPI.setAppExtInfo(jsonObject)

        }
        //友盟
        UMConfigure.init(applicationContext, UMConfigure.DEVICE_TYPE_PHONE, "5fd88824842ba953b88bc466")
        UMConfigure.setLogEnabled(true)

        initChild()
    }

    open fun initChild() {
    }

}