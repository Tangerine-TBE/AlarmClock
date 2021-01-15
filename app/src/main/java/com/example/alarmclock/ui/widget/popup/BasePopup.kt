package com.example.alarmclock.ui.widget.popup

import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.weight
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/26 17:21
 * @class describe
 */
open class BasePopup(val activity: Activity,layout:Int,width:Int,height:Int): PopupWindow(width, height) {
    protected val view=LayoutInflater.from(activity).inflate(layout,null)
    init {
        contentView = view
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isFocusable = true
        isOutsideTouchable = false
        intBgAnimation()
        setOnDismissListener {
            mOutValueAnimator?.start()
        }
    }

    open fun show(attachView: View,gravity:Int){
        if (!activity.isFinishing) {
            mInValueAnimator?.start()
            showAtLocation(attachView,gravity,0,0,)
        }
    }


    //设置窗口渐变
    private fun updateBgWindowAlpha(alpha: Float) {
        val window = activity.window
        val attributes = window.attributes
        attributes.alpha = alpha
        window.attributes = attributes
    }

    lateinit var mInValueAnimator: ValueAnimator
    lateinit var mOutValueAnimator: ValueAnimator
    private fun intBgAnimation() {
        mInValueAnimator = ValueAnimator.ofFloat(1.0f, 0.5f)
        mInValueAnimator?.duration = 300
        mInValueAnimator?.addUpdateListener { animation -> updateBgWindowAlpha(animation.animatedValue as Float) }
        mOutValueAnimator = ValueAnimator.ofFloat(0.5f, 1.0f)
        mOutValueAnimator?.duration = 300
        mOutValueAnimator?.addUpdateListener { animation -> updateBgWindowAlpha(animation.animatedValue as Float) }
    }



}