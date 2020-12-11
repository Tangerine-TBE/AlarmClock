package com.example.alarmclock.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import com.example.module_base.base.BaseApplication
import com.example.module_base.util.PackageUtil
import com.example.module_base.util.PermissionUtil
import com.example.module_base.util.SPUtil
import com.tamsiree.rxui.view.dialog.RxDialogSureCancel

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.util
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/12/9 20:11:37
 * @class describe
 */
class DialogUtil {

    companion object {
        fun createRemindDialog(context: Context) = RxDialogSureCancel(context).apply {
            setCancel("去打开")
            titleView.apply {
                textSize = 18f
                setTextColor(Color.BLACK)
                typeface = Typeface.defaultFromStyle(Typeface.BOLD);
            }

            setSure("我已打开")
            setTitle("温馨提示")
            setContent(
                "请确保${PackageUtil.getAppMetaData(
                    context,
                    Constants.APP_NAME
                )}在后台清理软件的白名单中，否则可能无法正常工作"
            )
            setCancelable(false)
            setCancelListener(View.OnClickListener {
                PermissionUtil.gotoPermission(context)
                dismiss()
            })

            setSureListener(View.OnClickListener {
                SPUtil.getInstance().putBoolean(Constants.DISMISS_DIALOG,true)
                dismiss()
            })

        }
    }

}