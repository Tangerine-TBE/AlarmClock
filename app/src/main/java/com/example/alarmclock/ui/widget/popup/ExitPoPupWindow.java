package com.example.alarmclock.ui.widget.popup;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import androidx.annotation.RequiresApi;

import com.example.alarmclock.R;
import com.example.module_ad.advertisement.AdType;
import com.example.module_ad.advertisement.FeedHelper;
import com.example.module_ad.utils.BaseBackstage;
import com.example.module_base.util.MyActivityManager;
import com.example.module_base.util.SizeUtils;
import com.tamsiree.rxkit.RxDeviceTool;

import static com.example.alarmclock.topfun.ThemeChangeKt.setCurrentThemeColor;


public class ExitPoPupWindow extends PopupWindow {

    private final View mView;
    private Button mCancelBt;
    private Button mSureBt;
    private final Activity mActivity;
    private FrameLayout mAdContainer;
    private GradientDrawable mDrawable;


    public ExitPoPupWindow(Activity activity) {
        super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.mActivity=activity;
        int screenHeight = RxDeviceTool.getScreenHeight(activity);
        mView = LayoutInflater.from(activity).inflate(R.layout.diy_exit_popup_window, null);
        setContentView(mView);
        setHeight(screenHeight);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(true);
        setOutsideTouchable(false);
        setAnimationStyle(R.style.ExitPopup);
        intBgAnimation();
        initView();
        initEvent();


    }

    private void initEvent() {
        mCancelBt.setOnClickListener(view -> {
            mOutValueAnimator.start();
            dismiss();
            mAdContainer.removeAllViews();
        });

        mSureBt.setOnClickListener(view -> {
            dismiss();
            BaseBackstage.isExit=true;
            MyActivityManager.removeAllActivity();
            if (mFeedHelper != null) {
                mFeedHelper.releaseAd();
            }
        });

        setOnDismissListener(() -> {
            mOutValueAnimator.start();
        });

    }

    private void initView( ) {
        mCancelBt = mView.findViewById(R.id.cancel);
        mSureBt = mView.findViewById(R.id.sure);
        mAdContainer = mView.findViewById(R.id.exitAd_container);
        mDrawable = new GradientDrawable();
    }

    private FeedHelper mFeedHelper;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void popupShowAd(Activity activity) {
        mInValueAnimator.start();
        mFeedHelper = new FeedHelper(activity, mAdContainer);
        mFeedHelper.showAd(AdType.EXIT_PAGE);
        mDrawable.setCornerRadius(SizeUtils.dip2px(activity,8f));
        mDrawable.setColor(setCurrentThemeColor(activity));
        mSureBt.setBackground(mDrawable);
    }

    //设置窗口渐变
    private void updateBgWindowAlpha(float alpha) {
        Window window = mActivity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = alpha;
        window.setAttributes(attributes);
    }


    private ValueAnimator mInValueAnimator;
    private ValueAnimator mOutValueAnimator;

    private void intBgAnimation() {
        mInValueAnimator = ValueAnimator.ofFloat(1.0f, 0.5f);
        mInValueAnimator.setDuration(300);
        mInValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBgWindowAlpha((Float) animation.getAnimatedValue());
            }
        });

        mOutValueAnimator = ValueAnimator.ofFloat(0.5f, 1.0f);
        mOutValueAnimator.setDuration(300);
        mOutValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBgWindowAlpha((Float) animation.getAnimatedValue());
            }
        });
    }



}