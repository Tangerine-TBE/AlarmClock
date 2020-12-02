package com.example.module_usercenter.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.core.app.ActivityCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module_base.base.BaseActivity;
import com.example.module_base.base.BaseApplication;
import com.example.module_base.provider.ModuleProvider;
import com.example.module_base.util.LogUtils;
import com.example.module_base.util.PackageUtil;
import com.example.module_usercenter.R;
import com.example.module_usercenter.bean.Data;
import com.example.module_usercenter.bean.LoginBean;
import com.example.module_usercenter.bean.OauthBean;
import com.example.module_usercenter.bean.RegisterBean;
import com.example.module_usercenter.bean.ThirdlyRegisterBean;
import com.example.module_usercenter.config.BaseUIConfig;
import com.example.module_usercenter.present.impl.LoginPresentImpl;
import com.example.module_usercenter.present.impl.RegisterPresentImpl;
import com.example.module_usercenter.present.impl.ThirdlyLoginPresentImpl;
import com.example.module_usercenter.ui.custom.DiyToolbar;
import com.example.module_usercenter.utils.Contents;
import com.example.module_usercenter.utils.EditTextUtil;
import com.example.module_usercenter.utils.Md5Util;
import com.example.module_usercenter.utils.SpUtil;
import com.example.module_usercenter.view.ILoginCallback;
import com.example.module_usercenter.view.IRegisterCallback;
import com.example.module_usercenter.view.IThirdlyLoginCallback;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.TokenResultListener;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.tamsiree.rxkit.RxNetTool;
import com.tamsiree.rxkit.view.RxToast;
import com.tamsiree.rxui.view.dialog.RxDialogShapeLoading;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;


@Route(path = ModuleProvider.ROUTE_LOGIN_ACTIVITY)
public class LoginActivity extends BaseActivity implements ILoginCallback, IThirdlyLoginCallback, IRegisterCallback {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private TokenResultListener mTokenResultListener;
    private PhoneNumberAuthHelper mPhoneNumberAuthHelper;

    private TextView tv_register;
    private TextView tv_change_pwd;
    private TextView tv_login_title;
    private EditText ed_number_input;
    private EditText ed_pwd_input;
    private DiyToolbar dt_toolbar;
    private Button bt_login;
    private RxDialogShapeLoading mRxDialog;
    private LoginPresentImpl mLoginPresent;
    private String mNumberStr;
    private String mMd5Pwd;
    private IWXAPI mWxapi;
    private ImageView iv_wx;
    private ImageView iv_qq;
    private ImageView iv_phone;
    private Tencent mTencent;
    private String mOpenId;
    private ThirdlyLoginPresentImpl mThirdlyLoginPresent;
    private BaseUIConfig mUIConfig;
    private RegisterPresentImpl mRegisterPresent;
    private boolean isOauth;

    @Override
    public int getLayoutView() {
        return R.layout.activity_login;
    }


    @Override
    public void initView() {
        tv_register = findViewById(R.id.tv_register);
        tv_change_pwd = findViewById(R.id.tv_change_pwd);
        dt_toolbar = findViewById(R.id.dt_toolbar);
        tv_login_title = findViewById(R.id.tv_login_title);
        ed_number_input = findViewById(R.id.ed_number_input);
        ed_pwd_input = findViewById(R.id.ed_pwd_input);
        bt_login = findViewById(R.id.bt_login);
        iv_wx = findViewById(R.id.iv_wx);
        iv_qq = findViewById(R.id.iv_qq);
        iv_phone = findViewById(R.id.iv_phone);
        EditTextUtil.setEditTextInputSpace(ed_pwd_input, 32);

        tv_login_title.setText("欢迎登录" + PackageUtil.getAppMetaData(this, Contents.APP_NAME));
        dt_toolbar.setColorBackground(Color.TRANSPARENT);
        dt_toolbar.setTitle("");


        mRxDialog = new RxDialogShapeLoading(this);
        mRxDialog.setLoadingText("正在登陆...");
        mRxDialog.setCancelable(false);

        //微信
        mWxapi = WXAPIFactory.createWXAPI(this, Contents.WECHAT_APP_ID, false);
        mWxapi.registerApp(Contents.WECHAT_APP_ID);
        //QQ初始化
        mTencent = Tencent.createInstance(Contents.QQ_ID, BaseApplication.Companion.getAppContext());
        //一键登录
        sdkInit(Contents.OAUTH);
        mUIConfig = BaseUIConfig.init(0, this, mPhoneNumberAuthHelper);
    }

    public void sdkInit(String secretInfo) {
        mTokenResultListener = new TokenResultListener() {
            @Override
            public void onTokenSuccess(String s) {
                //   hideLoadingDialog();
                TokenRet tokenRet = null;
                try {
                    tokenRet = TokenRet.fromJson(s);
                    if (ResultCode.CODE_START_AUTHPAGE_SUCCESS.equals(tokenRet.getCode())) {
                        Log.i("TAG", "唤起授权页成功：" + s);
                    }

                    if (ResultCode.CODE_SUCCESS.equals(tokenRet.getCode())) {
                        LogUtils.i("获取token成功：" + s);
                        if (!mRxDialog.isShowing()) {
                            mRxDialog.show();
                        }
                        getResultWithToken(tokenRet.getToken());
                        mPhoneNumberAuthHelper.setAuthListener(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTokenFailed(String s) {
                Log.e(TAG, "获取token失败：" + s);
                //  hideLoadingDialog();
                TokenRet tokenRet = null;
                try {
                    tokenRet = TokenRet.fromJson(s);
                    if (ResultCode.CODE_ERROR_USER_CANCEL.equals(tokenRet.getCode())) {
                        //模拟的是必须登录 否则直接退出app的场景
                        if (mRxDialog != null) {
                            mRxDialog.dismiss();
                        }
                        finish();
                    } else {
                        //  Toast.makeText(getApplicationContext(), "一键登录失败切换到其他登录方式", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "一键登录失败切换到其他登录方式", Toast.LENGTH_SHORT).show();
                        RxToast.warning("一键登录失败，请开启移动网络后重试或使用其他登陆方式");
                        //  Intent pIntent = new Intent(OneKeyLoginActivity.this, MessageActivity.class);
                        //  startActivityForResult(pIntent, 1002);
                        mPhoneNumberAuthHelper.quitLoginPage();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mPhoneNumberAuthHelper.setAuthListener(null);
            }
        };
        mPhoneNumberAuthHelper = PhoneNumberAuthHelper.getInstance(this, mTokenResultListener);
        mPhoneNumberAuthHelper.getReporter().setLoggerEnable(true);
        mPhoneNumberAuthHelper.setAuthSDKInfo(secretInfo);
    }


    /**
     * 进入app就需要登录的场景使用
     */
    private void oneKeyLogin() {
        mPhoneNumberAuthHelper = PhoneNumberAuthHelper.getInstance(getApplicationContext(), mTokenResultListener);
        mUIConfig.configAuthPage();
        getLoginToken(10000);
    }

    /**
     * 拉起授权页
     *
     * @param timeout 超时时间
     */
    public void getLoginToken(int timeout) {
        mPhoneNumberAuthHelper.getLoginToken(this, timeout);


    }

    public void getResultWithToken(final String token) {

        if (mLoginPresent != null) {
            mLoginPresent.getOauthNumber(token);
        }
        mPhoneNumberAuthHelper.quitLoginPage();
    }


    @Override
    public void initPresent() {

        mRegisterPresent = RegisterPresentImpl.getInstance();
        mRegisterPresent.registerCallback(this);

        mLoginPresent = LoginPresentImpl.getInstance();
        mLoginPresent.registerCallback(this);

        mThirdlyLoginPresent = ThirdlyLoginPresentImpl.getInstance();
        mThirdlyLoginPresent.registerCallback(this);

        EventBus.getDefault().register(this);
    }

    @Override
    public void initEvent() {
        //一键登录
        iv_phone.setOnClickListener(view -> {

            oneKeyLogin();
        });

        dt_toolbar.finishActivity(false);
        dt_toolbar.setOnBackClickListener(new DiyToolbar.OnBackClickListener() {
            @Override
            public void onActivityBackClick() {
                finish();
            }

            @Override
            public void onFragmentBackClick() {

            }
        });



        //注册
        tv_register.setOnClickListener(v -> {
                    isOauth = false;
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class).putExtra(Contents.ACTIVITY, Contents.REGISTER));
                }
        );

        //找回密码
        tv_change_pwd.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class).putExtra(Contents.ACTIVITY, Contents.CHANGE_PWD)));

        bt_login.setOnClickListener(v -> {
            mNumberStr = ed_number_input.getText().toString().trim();
            String pwd = ed_pwd_input.getText().toString().trim();
            mMd5Pwd = Md5Util.md5(pwd);
            if (mNumberStr.length() == 11 && mMd5Pwd.length() >= 6) {
                Map<String, String> map = new TreeMap<>();
                map.put(Contents.MOBILE, mNumberStr);
                map.put(Contents.PASSWORD, mMd5Pwd);
                if (mLoginPresent != null) {
                    mLoginPresent.toLogin(map);
                    isOauth=true;
                }
            } else {
                RxToast.warning("请输入11位手机号码和6个字符以上的密码");
            }

        });


        //微信
        iv_wx.setOnClickListener(v -> checkWXInstalled());


        //QQ
        iv_qq.setOnClickListener(v -> loginQQ());
    }

    /**
     * QQ登录
     */
    private IUiListener listener;

    private void loginQQ() {
        listener = new IUiListener() {
            @Override
            public void onComplete(Object object) {
                //  LogUtils.i(   LoginActivity.this,"登录成功: " + object.toString() );
                JSONObject jsonObject = (JSONObject) object;
                try {
                    //得到token、expires、openId等参数
                    String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                    String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                    mOpenId = jsonObject.getString(Constants.PARAM_OPEN_ID);
                    mTencent.setAccessToken(token, expires);
                    mTencent.setOpenId(mOpenId);

                    //检查是否注册
                    doCheckRegister();
                    mSPUtil.putBoolean(Contents.NOT_BACK, true);
                    //获取个人信息
                    getQQInfo();
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(UiError uiError) {
                //登录失败
                // LogUtils.i(   LoginActivity.this,"登录失败" + uiError.errorCode + "");

            }

            @Override
            public void onCancel() {
                //登录取消
                // LogUtils.i(   LoginActivity.this,"登录取消");
            }
        };
        //context上下文、第二个参数SCOPO 是一个String类型的字符串，表示一些权限
        //应用需要获得权限，由“,”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
        //第三个参数事件监听器
        mTencent.login(this, "all", listener);
        //注销登录
        //mTencent.logout(this);
    }

    /**
     * 获取QQ个人信息
     */
    private void getQQInfo() {
        //获取基本信息
        QQToken qqToken = mTencent.getQQToken();
        UserInfo info = new UserInfo(this, qqToken);
        info.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object object) {
                try {
                    // LogUtils.i(   LoginActivity.this,"个人信息：" + object.toString());
                    //头像
                    String avatar = ((JSONObject) object).getString("figureurl_2");
                    String nickName = ((JSONObject) object).getString("nickname");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
            }

            @Override
            public void onCancel() {
            }
        });
    }

    /**
     * 回调必不可少,官方文档未说明
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //腾讯QQ回调
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, listener);
            }
        }
    }

    //检查QQ是否已经注册
    private void doCheckRegister() {
        mRxDialog.show();
        if (mThirdlyLoginPresent != null) {
            Map<String, String> userInfo = new TreeMap<>();
            userInfo.put(Contents.OPENID, mOpenId);
            userInfo.put(Contents.TYPE, Contents.QQ_TYPE);
            mThirdlyLoginPresent.checkRegister(userInfo);
        }
    }


    private void checkWXInstalled() {
        if (!mWxapi.isWXAppInstalled()) {
            Toast.makeText(this, "您的设备未安装微信客户端", Toast.LENGTH_SHORT).show();
        } else {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            mWxapi.sendReq(req);
            finish();
        }
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        if (isOauth) {
            Map<String, String> userType = SpUtil.saveUserType(Contents.LOCAL_TYPE, mNumberStr, mMd5Pwd, "");
            SpUtil.saveUserInfo(loginBean, userType);
            mRxDialog.dismiss();
            finish();
        }
    }

    @Override
    public void onNumberSuccess(OauthBean oauthBean) {
        LogUtils.i("-----onNumberSuccess----------" + oauthBean.toString());
        if (oauthBean.getRet() == 200) {
            Data data = oauthBean.getData();
            mNumberStr = data.getMobile();
            String passwd = data.getPasswd();
            mMd5Pwd = Md5Util.md5(passwd);
            Map<String, String> map = new TreeMap<>();
            map.put(Contents.MOBILE, mNumberStr);
            map.put(Contents.CODE, data.getCode() + "");
            map.put(Contents.PASSWORD, passwd);
            map.put(Contents.PACKAGE, Contents.APP_PACKAGE);
            map.put(Contents.PLATFORM, PackageUtil.getAppMetaData(this, Contents.PLATFORM_KEY));
            if (mRegisterPresent != null) {
                isOauth = true;
                mRegisterPresent.registerNumber(map);

            }

        }
    }

    @Override
    public void onRegisterSuccess(RegisterBean registerBean) {
        if (isOauth) {
            LogUtils.i("-----RegisterBean----------" + registerBean.toString());
            if (registerBean.getRet() == 200 || registerBean.getRet() == 700) {
                Map<String, String> map = new TreeMap<>();
                map.put(Contents.MOBILE, mNumberStr);
                map.put(Contents.PASSWORD, mMd5Pwd);
                if (mLoginPresent != null) {
                    mLoginPresent.toLogin(map);
                }
            }
        }

    }


    @Override
    public void onLoginError() {
        mRxDialog.dismiss();
        RxToast.warning(this, "登陆失败").show();
    }

    @Override
    public void onLoading() {
        if (!mRxDialog.isShowing()) {
            mRxDialog.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dismissDialog(boolean isDis) {
        if (isDis) {
            if (mRxDialog != null) {
                mRxDialog.dismiss();
            }
        }
    }



    @Override
    public void onError() {

    }

    @Override
    public void release() {
        if (mLoginPresent != null) {
            mLoginPresent.unregisterCallback(this);
        }

        if (mThirdlyLoginPresent != null) {
            mThirdlyLoginPresent.unregisterCallback(this);
        }

        if (mRegisterPresent != null) {
            mRegisterPresent.unregisterCallback(this);
        }

        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onThirdlyLoginSuccess(LoginBean registerBean) {
        int ret = registerBean.getRet();
        if (ret == 200) {
            mRxDialog.dismiss();
            Map<String, String> userType = SpUtil.saveUserType(Contents.QQ_TYPE, "", "", mOpenId);
            SpUtil.saveUserInfo(registerBean, userType);
            finish();
            LogUtils.i("----------------------->：" + registerBean.getMsg());

        }
    }

    @Override
    public void onThirdlyLoginError() {
        RxToast.error(this, "QQ登陆失败").show();
    }

    @Override
    public void onCheckThirdlyRegisterSuccess(RegisterBean bean) {
        int ret = bean.getRet();
        if (ret == 200 && bean.getData().equals("1")) {
            doQQLogin();
        }
        if (ret == 200 && bean.getData().equals("0")) {
            doRegister();
        }
    }

    @Override
    public void onCheckError() {

    }

    @Override
    public void onThirdlyRegisterSuccess(ThirdlyRegisterBean registerBean) {
        mRxDialog.show();
        int ret = registerBean.getRet();
        LogUtils.i("onThirdlyRegisterSuccess----------------------->：" + ret);
        if (ret == 200) {
            doQQLogin();
            LogUtils.i("onThirdlyRegisterSuccess---------200-------------->：" + ret);
        }

        if (ret == 700) {
            if (registerBean.getMsg().equals("该帐号已经注册")) {
                doQQLogin();
                LogUtils.i("onThirdlyRegisterSuccess--------------700--------->：");
            }

        }
    }

    //QQ注册
    private void doRegister() {
        if (mThirdlyLoginPresent != null) {
            Map<String, String> userInfo = new TreeMap<>();
            userInfo.put(Contents.TYPE, Contents.QQ_TYPE);
            userInfo.put(Contents.OPENID, mOpenId);
            userInfo.put(Contents.PACKAGE, Contents.APP_PACKAGE);
            userInfo.put(Contents.PLATFORM, PackageUtil.getAppMetaData(this, Contents.PLATFORM_KEY));
            mThirdlyLoginPresent.toThirdlyRegister(userInfo);
        }
    }


    //QQ登陆
    private void doQQLogin() {
        if (mThirdlyLoginPresent != null) {
            Map<String, String> userInfo = new TreeMap<>();
            userInfo.put(Contents.OPENID, mOpenId);
            userInfo.put(Contents.TYPE, Contents.QQ_TYPE);
            mThirdlyLoginPresent.toThirdlyLogin(userInfo);
        }
    }

    @Override
    public void onThirdlyRegisterError() {
        RxToast.error(this, "QQ注册失败").show();
    }


    @Override
    public void onLoadCode(RegisterBean bean) {

    }


}
