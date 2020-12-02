package com.example.module_usercenter.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module_base.base.BaseActivity;
import com.example.module_base.provider.ModuleProvider;
import com.example.module_base.util.LogUtils;
import com.example.module_base.util.PackageUtil;
import com.example.module_usercenter.R;
import com.example.module_usercenter.bean.LoginBean;
import com.example.module_usercenter.bean.OauthBean;
import com.example.module_usercenter.bean.RegisterBean;
import com.example.module_usercenter.present.impl.FindPwdPresentImpl;
import com.example.module_usercenter.present.impl.LoginPresentImpl;
import com.example.module_usercenter.present.impl.RegisterPresentImpl;
import com.example.module_usercenter.ui.custom.DiyToolbar;
import com.example.module_usercenter.ui.custom.LoginView;
import com.example.module_usercenter.utils.Contents;
import com.example.module_usercenter.utils.Md5Util;
import com.example.module_usercenter.utils.SpUtil;
import com.example.module_usercenter.view.IFindPwdCallback;
import com.example.module_usercenter.view.ILoginCallback;
import com.example.module_usercenter.view.IRegisterCallback;
import com.tamsiree.rxkit.view.RxToast;
import com.tamsiree.rxui.view.dialog.RxDialogShapeLoading;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.TreeMap;

public class RegisterActivity extends BaseActivity implements IRegisterCallback, ILoginCallback, IFindPwdCallback {

    private String mTypeAction;
    private DiyToolbar dt_res_toolbar;
    private RegisterPresentImpl mRegisterPresent;
    private RxDialogShapeLoading mRxDialogLoading;
    private FindPwdPresentImpl mFindPwdPresent;
    private LoginPresentImpl mLoginPresent;
    private LoginView lv_register;
    private String phoneNumber;
    private String pwdNumber;



    @Override
    public int getLayoutView() {
      return   R.layout.activity_register;
    }


    @Override
    public void initView() {
        Intent intent = getIntent();
        mTypeAction = intent.getStringExtra(Contents.ACTIVITY);
        dt_res_toolbar= findViewById(R.id.dt_res_toolbar);
        lv_register= findViewById(R.id.lv_register);
        dt_res_toolbar.setColorBackground(Color.TRANSPARENT);
        if (mTypeAction.equals(Contents.CHANGE_PWD)) {
            dt_res_toolbar.setTitle("密码找回");
            lv_register.setLoginBtText("找回密码");
        } else {
            dt_res_toolbar.setTitle("账号注册");
            lv_register.setLoginBtText("注册");
        }

        mRxDialogLoading = new RxDialogShapeLoading(this);

    }

    @Override
    public void initPresent() {
        mRegisterPresent = RegisterPresentImpl.getInstance();
        mRegisterPresent.registerCallback(this);

        mLoginPresent = LoginPresentImpl.getInstance();
        mLoginPresent.registerCallback(this);


        mFindPwdPresent = FindPwdPresentImpl.getInstance();
        mFindPwdPresent.registerCallback(this);
    }




    @Override
    public void initEvent() {
        dt_res_toolbar.finishActivity(false);
        dt_res_toolbar.setOnBackClickListener(new DiyToolbar.OnBackClickListener() {
            @Override
            public void onActivityBackClick() {
                finish();
            }

            @Override
            public void onFragmentBackClick() {

            }
        });


        lv_register.setonStateClickListener(new LoginView.onStateClickListener() {
            @Override
            public void getVerificationCodeClick(String number) {
                if (!TextUtils.isEmpty(number)) {
                    if (mRegisterPresent != null) {
                        mRegisterPresent.getVerificationCode(number);
                        LogUtils.i( "---------------------->" + number);
                    }
                } else {
                    RxToast.warning( "请输入手机号码");
                }
            }

            @Override
            public void onLoginClick(String phone, String code, String password) {
                phoneNumber = phone;
                pwdNumber = Md5Util.md5(password);
                Map<String, String> map = new TreeMap<>();
                map.put(Contents.MOBILE, phone);
                map.put(Contents.CODE, code);
                if (mTypeAction.equals(Contents.CHANGE_PWD)) {
                    map.put(Contents.PASSWORD, pwdNumber);
                    if (mFindPwdPresent != null) {
                        mFindPwdPresent.findPwd(map);
                    }
                } else {
                    map.put(Contents.PASSWORD, password);
                    map.put(Contents.PACKAGE, Contents.APP_PACKAGE);
                    map.put(Contents.PLATFORM, PackageUtil.getAppMetaData(RegisterActivity.this, Contents.PLATFORM_KEY));
                    if (mRegisterPresent != null) {
                        mRegisterPresent.registerNumber(map);
                    }
                }

            }
        });

    }


    @Override
    public void onLoadCode(RegisterBean bean) {
        int ret = bean.getRet();
        if (ret==200) {
            RxToast.normal(this, "验证码已发送").show();
        } else if (ret == 700) {
            RxToast.warning(bean.getMsg()+"");
        }
    }

    @Override
    public void onFindPwdSuccess(RegisterBean registerBean) {
        if (mRxDialogLoading != null) {
            mRxDialogLoading.dismiss();
        }
        int ret = registerBean.getRet();
        if (ret == 200) {
            RxToast.success(this, "修改密码成功").show();
            finish();

        } else {
            RxToast.warning(this, registerBean.getMsg()).show();
        }
    }


    @Override
    public void onRegisterSuccess(RegisterBean registerBean) {
        int ret = registerBean.getRet();
        if (ret == 200) {
            Map<String, String> loginMap = new TreeMap<>();
            loginMap.put(Contents.MOBILE, phoneNumber);
            loginMap.put(Contents.PASSWORD, pwdNumber);
            if (mLoginPresent != null) {
                mLoginPresent.toLogin(loginMap);
            }
            RxToast.success(this, "注册成功").show();
        } else {
            if (mRxDialogLoading != null) {
                mRxDialogLoading.dismiss();
            }
            RxToast.warning(this, registerBean.getMsg()).show();
        }
        EventBus.getDefault().post(true);
    }

    @Override
    public void onLoading() {
        if (mRxDialogLoading != null) {
            mRxDialogLoading.show();
            mRxDialogLoading.setCancelable(false);
            mRxDialogLoading.setLoadingText("正在注册账号");
        }
    }

    @Override
    public void onError() {
        if (mRxDialogLoading != null) {
            mRxDialogLoading.dismiss();
        }
        RxToast.error(this,"登陆失败").show();
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        if (mRxDialogLoading != null) {
            mRxDialogLoading.dismiss();
        }
        if (loginBean.getRet()==200) {
            Map<String, String> userType = SpUtil.saveUserType(Contents.LOCAL_TYPE, phoneNumber, pwdNumber, "");
            SpUtil.saveUserInfo(loginBean, userType);
            LogUtils.i("onLoginSuccess----------------------?"+   loginBean.getData().getId());
            boolean isBuyPager =mSPUtil.getBoolean(Contents.BUY_PAGER, false);
            if (isBuyPager) {
                startActivity(new Intent(this, BuyVipActivity.class));
            } else {
                ARouter.getInstance().build(ModuleProvider.ROUTE_MAIN_ACTIVITY).withInt(ModuleProvider.FRAGMENT_ID,3).navigation();
            }
            finish();
        }
    }

    @Override
    public void onNumberSuccess(OauthBean oauthBean) {

    }

    @Override
    public void onLoginError() {
        if (mRxDialogLoading != null) {
            mRxDialogLoading.dismiss();
        }
    }


    @Override
    public void release() {
        if (mLoginPresent != null) {
            mLoginPresent.unregisterCallback(this);
        }

        if (mRegisterPresent != null) {
            mRegisterPresent.unregisterCallback(this);
        }

        if (mFindPwdPresent != null) {
            mFindPwdPresent.unregisterCallback(this);
        }

    }

}
