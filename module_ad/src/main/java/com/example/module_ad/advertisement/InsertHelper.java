package com.example.module_ad.advertisement;

import android.app.Activity;

import com.example.module_ad.base.IBaseAdBean;
import com.example.module_ad.base.IBaseXXBean;
import com.example.module_ad.base.IShowAdCallback;
import com.example.module_ad.bean.AdBean;
import com.example.module_ad.utils.AdMsgUtil;
import com.example.module_ad.utils.AdProbabilityUtil;
import com.example.module_usercenter.utils.SpUtil;

import java.util.Map;

public class InsertHelper {

    private Activity mActivity;
    private IBaseAdBean mManager_page;
    private IBaseXXBean mBaseInsert_screen;
    private TTInsertAd mTtInsertAd;
    private boolean mAddToutiaoAdError=false;
    private boolean mAddTengxunAdError=false;
    private TXInsertAd mTxInsertAd;


    public InsertHelper(Activity activity) {
        this.mActivity=activity;
    }


    public void showAd(AdType type) {
        if (SpUtil.isVIP()) {
            return;
        }
        if (AdMsgUtil.isHaveAdData()) {
            mManager_page=AdMsgUtil.switchAdType(type, AdMsgUtil.getAdState());
            mBaseInsert_screen = mManager_page.getBaseInsert_screen();
            String baseAd_percent = mBaseInsert_screen.getBaseAd_percent();
            double probability = AdProbabilityUtil.showAdProbability(baseAd_percent);
            if (mBaseInsert_screen.isBaseStatus()) {
                double random = Math.random();
                if (random >probability) {
                    showTTInsertAd();
                } else {
                    showTXInsertAd();
                }
            }

        }

    }


    private void showTTInsertAd() {
        mTtInsertAd = new TTInsertAd(mActivity);
        mTtInsertAd.showAd();
        mTtInsertAd.setOnShowError(new IShowAdCallback() {
            @Override
            public void onShowError() {
                if (!mAddToutiaoAdError) {
                    showTXInsertAd();
                }
                mAddToutiaoAdError=true;
            }
        });
    }


    private void showTXInsertAd() {
        mTxInsertAd = new TXInsertAd(mActivity);
        mTxInsertAd.showAd();
        mTxInsertAd.setOnShowError(new IShowAdCallback() {
            @Override
            public void onShowError() {
                if (!mAddTengxunAdError) {
                    showTTInsertAd();
                }
                mAddTengxunAdError=true;
            }
        });

    }

    public void releaseAd() {
        if (mTtInsertAd != null) {
            mTtInsertAd.releaseAd();
        }

        if (mTxInsertAd!=null) {
            mTxInsertAd.releaseAd();
        }
    }

}
