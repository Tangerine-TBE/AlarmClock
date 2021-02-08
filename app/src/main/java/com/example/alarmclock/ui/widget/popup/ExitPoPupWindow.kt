package com.example.alarmclock.ui.widget.popup

import android.app.Activity
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.alarmclock.R
import com.example.alarmclock.topfun.setCurrentThemeColor
import com.example.module_ad.advertisement.AdType
import com.example.module_ad.advertisement.FeedHelper
import com.example.module_ad.utils.BaseBackstage
import com.example.module_base.util.MyActivityManager
import com.example.module_base.util.SizeUtils
import com.tamsiree.rxkit.RxDeviceTool.getScreenHeight
import kotlinx.android.synthetic.main.diy_exit_popup_window.view.*

class ExitPoPupWindow(mActivity: Activity) : BasePopup(mActivity,R.layout.diy_exit_popup_window,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
    private var mDrawable: GradientDrawable
    init {
        val screenHeight = getScreenHeight(mActivity)
        height = screenHeight
        animationStyle = R.style.ExitPopup
        mDrawable = GradientDrawable()
        initEvent()
    }

    private fun initEvent() {
        view?.apply {
            mCancel.setOnClickListener {
                mOutValueAnimator?.start()
                this@ExitPoPupWindow.dismiss()
                exitAd_container?.removeAllViews()
            }
            mSure.setOnClickListener {
                mOutValueAnimator?.start()
                dismiss()
                BaseBackstage.isExit = true
                MyActivityManager.removeAllActivity()

            }

        }
        setOnDismissListener {
            if (mFeedHelper!= null) {
                mFeedHelper?.releaseAd()
                mFeedHelper=null
            }
        }

    }

    private var mFeedHelper: FeedHelper? = null
    private fun popupShowAd() {
        view?.apply {
            mFeedHelper = FeedHelper(activity, exitAd_container)
            mFeedHelper?.showAd(AdType.EXIT_PAGE)
            mDrawable.cornerRadius = SizeUtils.dip2px(activity, 8f).toFloat()
            mDrawable.setColor(ContextCompat.getColor(context, if(setCurrentThemeColor().bgcolor == R.color.white) setCurrentThemeColor().titlecolor else setCurrentThemeColor().bgcolor))
            mSure.background = mDrawable
        }
    }


    override fun show(attachView: View, gravity: Int) {
        popupShowAd()
        super.show(attachView, gravity)

    }



}