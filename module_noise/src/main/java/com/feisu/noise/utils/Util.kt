package com.feisu.noise.utils

import android.content.Context
import com.example.module_base.base.BaseApplication
import com.tamsiree.rxkit.RxNetTool
import com.tamsiree.rxkit.view.RxToast
import java.io.File

/**
 * @name AlarmClock
 * @class name：com.feisu.noise.utils
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/23 11:20:16
 * @class describe
 */

    fun createFile(path:String): File {
        val externalFilesDir = BaseApplication.application.getExternalFilesDir("music")
        return File(externalFilesDir,path)
    }


    fun checkNetWork(context: Context,block:()->Unit){
        if (!RxNetTool.isNetworkAvailable(context)) {
            RxToast.normal("网络连接错误，请检查网络是否连接！")
        } else {
            block()
        }
    }