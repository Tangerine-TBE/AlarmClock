package com.example.module_usercenter.ui.activity;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module_base.base.BaseViewActivity;
import com.example.module_base.base.BaseApplication;
import com.example.module_base.provider.ModuleProvider;
import com.example.module_base.util.LogUtils;
import com.example.module_base.util.MyStatusBarUtil;
import com.example.module_base.util.PackageUtil;
import com.example.module_base.util.SPUtil;
import com.example.module_usercenter.R;
import com.example.module_usercenter.bean.LoginBean;
import com.example.module_usercenter.bean.OauthBean;
import com.example.module_usercenter.bean.PriceBean;
import com.example.module_usercenter.bean.RegisterBean;
import com.example.module_usercenter.bean.ThirdlyRegisterBean;
import com.example.module_usercenter.bean.WeiXinBean;
import com.example.module_usercenter.present.impl.LoginPresentImpl;
import com.example.module_usercenter.present.impl.ThirdlyLoginPresentImpl;
import com.example.module_usercenter.present.impl.WeChatPresentImpl;
import com.example.module_usercenter.ui.adapter.VipPriceAdapter;
import com.example.module_usercenter.ui.custom.DiyToolbar;
import com.example.module_usercenter.utils.ColorUtil;
import com.example.module_usercenter.utils.Contents;
import com.example.module_usercenter.utils.SpUtil;
import com.example.module_usercenter.view.ILoginCallback;
import com.example.module_usercenter.view.IThirdlyLoginCallback;
import com.example.module_usercenter.view.IWeChatCallback;
import com.just.agentweb.AgentWeb;
import com.tamsiree.rxkit.view.RxToast;
import com.tamsiree.rxui.view.dialog.RxDialogShapeLoading;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class BuyVipViewActivity extends BaseViewActivity implements ILoginCallback, IThirdlyLoginCallback, IWeChatCallback {

    private DiyToolbar mDiyToolbar;
    private RecyclerView rv_price_container;
    private ImageView scb_zfb;
    private ImageView scb_wx;
    private TextView tv_buy;
    private LinearLayout web_container;
    private List<PriceBean> mPriceBeanList = new ArrayList<>();
    private VipPriceAdapter mVipPriceAdapter;
    private PriceBean mBean;
    private String mPlay = Contents.ALI_PAY;
    private String mUrl = Contents.PAY_ALI_URL;
    private int mVipLevel;
    private LoginPresentImpl mLoginPresent;
    private ThirdlyLoginPresentImpl mThirdlyLoginPresent;
    private WeChatPresentImpl mWeChatPresent;
    private boolean mIsLogin;
    private boolean isZfb = true;
    private boolean isWx = true;
    private boolean isPay;
    private String mId_type;
    private String mAccount;
    private String mPwd;
    private String mOpenid;
    private RxDialogShapeLoading mRxDialogLoading;
    private boolean isBuy;
    private RxDialogShapeLoading mRxDialogShapeLoading;
    private boolean mIsToBuy;

    @Override
    public int getLayoutView() {
        return R.layout.activity_buy_vip;
    }


    @Override
    public void initView() {
        //开启后台广告
        SPUtil.getInstance().putBoolean(Contents.NO_BACK,true);
        Intent intent = getIntent();
        mIsToBuy = intent.getBooleanExtra(Contents.TO_BUY, false);
        mDiyToolbar = findViewById(R.id.vip_toolbar);
        rv_price_container = findViewById(R.id.rv_price_container);
        scb_zfb = findViewById(R.id.scb_zfb);
        scb_wx = findViewById(R.id.scb_wx);
        tv_buy = findViewById(R.id.tv_buy);
        web_container = findViewById(R.id.web_container);
        scb_zfb.setImageResource(R.mipmap.icon_ck_select);


        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mDiyToolbar.getLayoutParams();
        layoutParams.topMargin= MyStatusBarUtil.getStatusBarHeight(this);
        mDiyToolbar.setLayoutParams(layoutParams);
        mDiyToolbar.setColorBackground(ColorUtil.TRANSPARENT);
        mDiyToolbar.setTitleColor(Color.WHITE);
        mDiyToolbar.setTitle("会员");

        mBean = new PriceBean(Contents.VIP_title_13, Contents.VIP13, Contents.VIP_price_13);
        mPriceBeanList.add(new PriceBean(Contents.VIP_title_13, Contents.VIP13,  Contents.VIP_price_13));
        mPriceBeanList.add(new PriceBean(Contents.VIP_title_1, Contents.VIP1,  Contents.VIP_price_1));
        mPriceBeanList.add(new PriceBean(Contents.VIP_title_3, Contents.VIP3,  Contents.VIP_price_3));
        mPriceBeanList.add(new PriceBean(Contents.VIP_title_12, Contents.VIP12,  Contents.VIP_price_12));


        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_price_container.setLayoutManager(manager);
        mVipPriceAdapter = new VipPriceAdapter(mPriceBeanList);
        rv_price_container.setAdapter(mVipPriceAdapter);

        mRxDialogLoading = new RxDialogShapeLoading(this);
        mRxDialogLoading.setCancelable(false);
        mRxDialogLoading.setLoadingText("正在拉起支付页面...");


        mRxDialogShapeLoading=new RxDialogShapeLoading(this);
        mRxDialogShapeLoading.setCancelable(false);
        mRxDialogShapeLoading.setLoadingText("正在校验数据...");

    }


    private void setVipInfo() {
        int vipLevel = mSPUtil.getInt(Contents.USER_VIP_LEVEL,0);
       if(vipLevel>0){
           tv_buy.setBackgroundResource(R.drawable.shape_vip_ever_bt_bg);
       }else {
           tv_buy.setBackgroundResource(R.drawable.shape_vip_buy_bt_bg);
        }


    }


    @Override
    public void initPresent() {
        mLoginPresent = LoginPresentImpl.getInstance();
        mLoginPresent.registerCallback(this);

        mThirdlyLoginPresent = ThirdlyLoginPresentImpl.getInstance();
        mThirdlyLoginPresent.registerCallback(this);

        mWeChatPresent = WeChatPresentImpl.getInstance();
        mWeChatPresent.registerCallback(this);
    }



    @Override
    public void initEvent() {
        //返回
        mDiyToolbar.finishActivity(false);
        mDiyToolbar.setOnBackClickListener(new DiyToolbar.OnBackClickListener() {
            @Override
            public void onActivityBackClick() {
                finish();
            }

            @Override
            public void onFragmentBackClick() {

            }
        });

        mVipPriceAdapter.setOnItemClickListener(bean -> mBean = bean);

        //支付宝选择
        scb_zfb.setOnClickListener(v-> {
            if (isZfb) {
                mPlay = Contents.ALI_PAY;
                mUrl = Contents.PAY_ALI_URL;
            }
              scb_zfb.setImageResource(isZfb ? R.mipmap.icon_ck_select : R.mipmap.icon_ck_normal);
              scb_wx.setImageResource(R.mipmap.icon_ck_normal);
            isWx = true;
        });

        //微信选择
        scb_wx.setOnClickListener(v -> {
            if (isWx) {
                mPlay = Contents.WX_PAY;
                mUrl = Contents.PAY_WX_URL;
            }
            scb_wx.setImageResource(isWx ? R.mipmap.icon_ck_select : R.mipmap.icon_ck_normal);
            scb_zfb.setImageResource(R.mipmap.icon_ck_normal);
            isZfb = true;

        });

        //购买会员
        tv_buy.setOnClickListener(v -> {
            mIsLogin = mSPUtil.getBoolean(Contents.USER_IS_LOGIN, false);
            if (mIsLogin) {
                mVipLevel = mSPUtil.getInt(Contents.USER_VIP_LEVEL, 0);
                if (mVipLevel >0) {
                    RxToast.info("您已经是尊贵的VIP了");
                } else {
                    toPay();
                    isPay = true;
                    isBuy = true;
                }
            } else {
                startActivity(new Intent(BuyVipViewActivity.this, LoginViewActivity.class));
                mSPUtil.putBoolean(Contents.BUY_PAGER, true);
                isBuy = false;
            }
        });


    }


    /**
     * 生成订单号
     */
    private String getTrade() {
        String channel = PackageUtil.getAppMetaData(this, Contents.PLATFORM_KEY);
        String str = channel.substring(channel.indexOf("_") + 1);
        if (str.equals("360")) {
            str = "SLL";
        }
        str = str.toUpperCase();
        String id = mSPUtil.getString(Contents.USER_ID, "");
        return mBean.getVipLevel() + "_" + id + "_" + str + "_" + mPlay + "_" + new Random().nextInt(100000);
    }


    private boolean checkPayTool(){
        PackageManager packageManager = getPackageManager();
        if (packageManager!=null) {
            try {
                if (mPlay == Contents.ALI_PAY) {
                    PackageInfo packageInfo = packageManager.getPackageInfo("com.tencent.mm", PackageManager.MATCH_UNINSTALLED_PACKAGES);
                   return packageInfo!=null;

                } else if (mPlay == Contents.WX_PAY) {
                    PackageInfo packageInfo = packageManager.getPackageInfo("com.eg.android.AlipayGphone", PackageManager.MATCH_UNINSTALLED_PACKAGES);
                    return packageInfo!=null;
                } else {
                    return false;
                }
            }catch (Exception e){
                return false;
            }
        }
        return false;
    }


    private void toPay() {
        if (checkPayTool()) {
            String app_name = PackageUtil.getAppMetaData(this, Contents.APP_NAME);
            String url = mUrl + Contents.TRADE + "=" + getTrade() + "&" + Contents.SUBJECT + "=" + app_name + mBean.getVipLevel() + "&" + Contents.PRICE + "=" + mBean.getPrice() + "&" + Contents.BODY + "=" + app_name + mBean.getTitle();
            LogUtils.i("toPay--------->" + url);

            AgentWeb.with(this)
                    .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator()
                    .createAgentWeb()
                    .ready()
                    .go(url);

            if (!isFinishing()) {
                mRxDialogLoading.show();
            }
        } else {
            RxToast.warning("未安装此支付工具");
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isBuy) {
            if (!isFinishing()) {
                mRxDialogShapeLoading.show();
            }
                LogUtils.i("onResume-------------------?");
                BaseApplication.Companion.getMainHandler().postDelayed(() -> checkVIP(), 2000);
        }
        setVipInfo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRxDialogLoading != null&mRxDialogLoading.isShowing()) {
            mRxDialogLoading.dismiss();
        }
        LogUtils.i("onPause-------------------?");

    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.i("onStop-------------------?");

    }


    //检查是否购买了Vip
    private void checkVIP() {
        mId_type = mSPUtil.getString(Contents.USER_ID_TYPE, "");
        if (isPay & !TextUtils.isEmpty(mId_type)) {

      //      isBuy = false;
            mAccount = mSPUtil.getString(Contents.USER_ACCOUNT, "");
            mPwd = mSPUtil.getString(Contents.USER_PWD, "");
            mOpenid = mSPUtil.getString(Contents.USER_THIRDLY_OPENID, "");
            LogUtils.i("onResume--------->" + mId_type + "       " + mAccount + "     " + mPwd + "        " + mOpenid);

            Map<String, String> userInfo = new TreeMap<>();
            userInfo.put(Contents.OPENID, mOpenid);

            if (mId_type.equals(Contents.LOCAL_TYPE)) {
                Map<String, String> map = new TreeMap<>();
                map.put(Contents.MOBILE, mAccount);
                map.put(Contents.PASSWORD, mPwd);
                if (mLoginPresent != null) {
                    mLoginPresent.toLogin(map);
                }
            } else if (mId_type.equals(Contents.QQ_TYPE)) {
                userInfo.put(Contents.TYPE, Contents.QQ_TYPE);
                mThirdlyLoginPresent.toThirdlyLogin(userInfo);
            } else if (mId_type.equals(Contents.WECHAT_TYPE)) {
                userInfo.put(Contents.TYPE, Contents.WECHAT_TYPE);
                mWeChatPresent.toWxLogin(userInfo);
            }


        }

    }


    //本地
    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        Map<String, String> userType = SpUtil.saveUserType(Contents.LOCAL_TYPE, mAccount, mPwd, "");
        SpUtil.saveUserInfo(loginBean, userType);
        payFinishTip(loginBean);
    }

    @Override
    public void onNumberSuccess(OauthBean oauthBean) {

    }

    //QQ
    @Override
    public void onThirdlyLoginSuccess(LoginBean loginBean) {
        Map<String, String> userType = SpUtil.saveUserType(Contents.QQ_TYPE, "", "", mOpenid);
        SpUtil.saveUserInfo(loginBean, userType);
        payFinishTip(loginBean);
    }


    //微信
    @Override
    public void onWxLoginSuccess(LoginBean loginBean) {
        Map<String, String> userType = SpUtil.saveUserType(Contents.WECHAT_TYPE, "", "", mOpenid);
        SpUtil.saveUserInfo(loginBean, userType);
        payFinishTip(loginBean);
    }

    private void payFinishTip(LoginBean loginBean) {
        LoginBean.DataBean data = loginBean.getData();
        if (isBuy) {
            if (data != null) {
                if (mRxDialogShapeLoading != null&mRxDialogShapeLoading.isShowing()) {
                    mRxDialogShapeLoading.dismiss();
                }
                int vip = data.getVip();
                if (vip > 0) {
                    RxToast.success("支付成功");
                    if (mIsToBuy) {
                        ARouter.getInstance().build(ModuleProvider.ROUTE_MAIN_ACTIVITY).withInt(ModuleProvider.FRAGMENT_ID,0).navigation();
                        finish();
                    }
                    setVipInfo();

                } else {
                    RxToast.warning("支付失败");
                }
                isPay = false;
            }
            isBuy=false;
        }

    }

    @Override
    public void onLoginError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError() {

    }


    @Override
    public void onThirdlyLoginError() {

    }

    @Override
    public void onCheckThirdlyRegisterSuccess(RegisterBean bean) {

    }

    @Override
    public void onCheckError() {

    }

    @Override
    public void onThirdlyRegisterSuccess(ThirdlyRegisterBean bean) {

    }

    @Override
    public void onThirdlyRegisterError() {

    }

    @Override
    public void onWxAccreditSuccess(WeiXinBean weiXinBean) {

    }

    @Override
    public void onWxAccreditError() {

    }

    @Override
    public void onCheckWxRegisterSuccess(RegisterBean bean) {

    }

    @Override
    public void onCheckWxError() {

    }

    @Override
    public void onWxRegisterSuccess(ThirdlyRegisterBean thirdlyRegisterBean) {

    }

    @Override
    public void onWxRegisterError() {

    }


    @Override
    public void onWxLoginError() {

    }


    @Override
    public void release() {
        mSPUtil.putBoolean(Contents.NO_BACK, false);
        if (mRxDialogLoading != null&mRxDialogLoading.isShowing()) {
            mRxDialogLoading.dismiss();
        }

        if (mRxDialogShapeLoading != null&mRxDialogShapeLoading.isShowing()) {
            mRxDialogShapeLoading.dismiss();
        }


        if (mLoginPresent != null) {
            mLoginPresent.unregisterCallback(this);
        }

        if (mThirdlyLoginPresent != null) {
            mThirdlyLoginPresent.unregisterCallback(this);
        }

        if (mWeChatPresent != null) {
            mWeChatPresent.unregisterCallback(this);
        }

    }


}
