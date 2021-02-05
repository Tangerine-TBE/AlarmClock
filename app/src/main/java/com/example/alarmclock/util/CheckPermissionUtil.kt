package com.example.alarmclock.util

import android.content.pm.PackageManager
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.alarmclock.repository.DataProvider
import com.example.module_base.base.BaseApplication
import com.permissionx.guolindev.PermissionX


/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.util
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/12/9 14:28:35
 * @class describe
 */
object CheckPermissionUtil {


    fun checkRuntimePermission(act: FragmentActivity?, permissions:ArrayList<String>, isShow:Boolean,showFailMsg:()->Unit, goActivity: ()->Unit) {
        PermissionX.init(act)
            .permissions(permissions)
            .setDialogTintColor(
                Color.parseColor("#FF0994"),
                Color.parseColor("#FF0994")
            ).onExplainRequestReason { scope, deniedList, beforeRequest ->
                if (isShow) {
                    val msg = "即将申请的权限是程序必须依赖的权限"
                    scope.showRequestReasonDialog(deniedList, msg, "开启", "取消")
                    showFailMsg()
                } else {
                    goActivity()
                }
            }
            .onForwardToSettings { scope, deniedList ->
                if (isShow) {
                    val msg = "您需要去应用程序设置当中手动开启权限"
                    scope.showForwardToSettingsDialog(deniedList, msg, "开启", "取消")
                    showFailMsg()
                } else {
                    goActivity()
                }
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) goActivity()
            }
    }


    fun lacksPermissions(): Boolean {
        for ( permission in DataProvider.calendarPermission) {
           if (lacksPermission(permission)){
               return true
           }
        }
       return false
    }
    /**
     * 判断是否缺少权限
     */
    private fun lacksPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(BaseApplication.getContext(), permission) ===
                PackageManager.PERMISSION_GRANTED }

}