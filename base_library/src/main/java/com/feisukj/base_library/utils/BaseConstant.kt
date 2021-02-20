package com.feisukj.base_library.utils

import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import com.example.module_base.base.BaseApplication

object BaseConstant {
    val application by lazy { BaseApplication.application }
    val mainHandler: Handler by lazy { Handler(Looper.getMainLooper()) }
    val channel by lazy { application.packageManager.getApplicationInfo(application.packageName,
        PackageManager.GET_META_DATA).metaData.getString("channel","um") }
    const val FeiSuHost="http://114.215.47.46:8080"
    val versionName by lazy { application.packageManager.getPackageInfo(application.packageName,PackageManager.GET_ACTIVITIES).versionName }

    fun getString(resId:Int):String{
        return application.getString(resId)
    }

    //友盟统计事件
    const val apkClick_UM="setting_apk_click"
    const val miniProgressClick_UM="setting_mini_progress_click"
    const val videoClick_UM="setting_see_video_click"

    //广点通开屏
    const val SPLASH_REQUEST_GDT="spreadRequest_gdt"//请求
    const val SPLASH_REQUEST_SUCCESS_GDT="spreadRequestSuccess_gdt"//请求成功
    const val SPLASH_ERROR_GDT="spreadRequestError_gdt"//广告加载失败
    const val SPLASH_SHOW_GDT="spreadShow_gdt"//广告展示成功
    //广点通原生
    const val NATIVE_REQUEST_GDT="nativeRequest_gdt"//请求
    const val NATIVE_REQUEST_SUCCESS_GDT="nativeRequestSuccess_gdt"//广告数据加载成功
    const val NATIVE_ERROR_GDT="nativeRequestError_gdt"//请求失败,无广告填充
    const val NATIVE_SHOW_GDT="nativeShow_gdt"//广告展示成功
    const val NATIVE_FAIL_GDT="nativeonRenderFail_gdt"//渲染广告失败
    //头条开屏
    const val SPLASH_REQUEST_TT="spreadRequest_toutiao"//请求开屏广告
    const val SPLASH_REQUEST_SUCCESS_TT="spreadRequestSuccess_toutiao"//开屏广告请求成功
    const val SPLASH_ERROR_TT="spreadRequestError_toutiao"//请求失败
    const val SPLASH_SHOW_TT="spreadShow_toutiao"//开屏广告展示成功
    const val SPLASH_TIME_OUT_TT="spreadTimeOut_toutiao"//开屏广告加载超时
    //头条原生
    const val NATIVE_REQUEST_TT="nativeRequest_toutiao"//请求原生广告
    const val NATIVE_REQUEST_SUCCESS_TT="nativeRequestSuccess_toutiao"//请求成功
    const val NATIVE_ERROR_TT="nativeRequestError_toutiao"//请求失败
    const val NATIVE_SHOW_TT="nativeShow_toutiao"//展示原生广告
}