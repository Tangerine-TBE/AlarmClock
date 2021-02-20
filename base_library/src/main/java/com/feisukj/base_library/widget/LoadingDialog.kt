package com.feisukj.base_library.widget

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.feisukj.base_library.R

class LoadingDialog (private val context: Context) {
    private var mLoadingView: LVCircularRingView? = null
    private val mLoadingDialog: Dialog by lazy { Dialog(context, R.style.loadingDialog) }
    private var msg = "加载中···"
    private var cancelable = true
    var isShowing: Boolean = false
        private set

    /**
     * 设置提示信息
     */
    fun setTitleText(msg: String): LoadingDialog {
        this.msg = msg
        return this
    }

    fun setTitleText(msg:Int):LoadingDialog{
        this.msg = context.getString(msg)
        return this
    }

    /**
     * 返回键是否可用
     */
    fun setCancelable(cancelable: Boolean): LoadingDialog {
        this.cancelable = cancelable
        return this
    }

    fun show() {
        val view = View.inflate(context, R.layout.dialog_loading, null)
        // 获取整个布局
        val layout = view.findViewById<LinearLayout>(R.id.dialog_view)
        // 页面中的LoadingView
        mLoadingView = view.findViewById(R.id.lvcr_loading)
        // 页面中显示文本
        val loadingText = view.findViewById<TextView>(R.id.loading_text)
        // 显示文本
        loadingText.text = msg
        // 设置返回键无效
        mLoadingDialog.setCancelable(cancelable)
        mLoadingDialog.setCanceledOnTouchOutside(cancelable)
        mLoadingDialog.setContentView(layout, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT))
        mLoadingDialog.show()
        mLoadingView?.startAnim()
        isShowing = true
    }

    fun dismiss() {
        if (isShowing) {
            mLoadingView?.stopAnim()
            mLoadingDialog.dismiss()
            isShowing = false
        }
    }
}