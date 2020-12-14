package com.example.alarmclock.ui.widget.popup

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alarmclock.R
import com.example.alarmclock.topfun.setCurrentThemeColor
import com.example.module_ad.advertisement.AdType
import com.example.module_ad.advertisement.FeedHelper
import com.example.module_ad.utils.BaseBackstage
import com.example.module_base.util.MyActivityManager
import com.example.module_base.util.SizeUtils
import com.tamsiree.rxkit.RxDeviceTool.getScreenHeight
import kotlinx.android.synthetic.main.diy_exit_popup_window.view.*

class ExitPoPupWindow(mActivity: Activity) : BasePopup(mActivity,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
    private val mView: View=LayoutInflater.from(mActivity).inflate(R.layout.diy_exit_popup_window, null)
    private var mDrawable: GradientDrawable
    init {
        val screenHeight = getScreenHeight(mActivity)
        contentView = mView
        height = screenHeight
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isFocusable = true
        isOutsideTouchable = false
        animationStyle = R.style.ExitPopup
        mDrawable = GradientDrawable()
        initEvent()
    }

    private fun initEvent() {
        mView?.apply {
            mCancel.setOnClickListener {
                mOutValueAnimator!!.start()
                dismiss()
                exitAd_container!!.removeAllViews()
            }
            mSure.setOnClickListener {
                dismiss()
                BaseBackstage.isExit = true
                MyActivityManager.removeAllActivity()

            }

        }
        setOnDismissListener {
            mOutValueAnimator.start()
            if (mFeedHelper!= null) {
                mFeedHelper?.releaseAd()
                mFeedHelper=null
            }
        }

    }

    private var mFeedHelper: FeedHelper? = null
    fun popupShowAd(activity: Activity) {
        mInValueAnimator.start()
        mFeedHelper = FeedHelper(activity, mView.exitAd_container)
        mFeedHelper?.showAd(AdType.EXIT_PAGE)
        mDrawable.cornerRadius = SizeUtils.dip2px(activity, 8f).toFloat()
        mDrawable.setColor(setCurrentThemeColor(activity))
        mView.mSure.background = mDrawable
    }



}