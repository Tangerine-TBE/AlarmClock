package com.example.module_usercenter.config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Toast;
import com.example.module_usercenter.R;
import com.example.module_usercenter.utils.ColorUtil;
import com.mobile.auth.gatewayauth.AuthRegisterViewConfig;
import com.mobile.auth.gatewayauth.AuthRegisterXmlConfig;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.CustomInterface;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.ui.AbstractPnsViewDelegate;

public class DialogBottomConfig extends BaseUIConfig {

    public DialogBottomConfig(Activity activity, PhoneNumberAuthHelper authHelper) {
        super(activity, authHelper);
    }

    @Override
    public void configAuthPage() {
        mAuthHelper.removeAuthRegisterXmlConfig();
        mAuthHelper.removeAuthRegisterViewConfig();
        int authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
        if (Build.VERSION.SDK_INT == 26) {
            authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_BEHIND;
        }
        updateScreenSize(authPageOrientation);
        int dialogHeight = (int) (mScreenHeightDp * 0.5f);
        //sdk默认控件的区域是marginTop50dp
        int designHeight = dialogHeight - 50;
        int unit = designHeight / 10;
        int logBtnHeight = (int) (unit * 1.2);
        mAuthHelper.addAuthRegistViewConfig("switch_msg", new AuthRegisterViewConfig.Builder()
                .setView(initSwitchView(unit * 6))
                .setRootViewId(AuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_BODY)
                .setCustomInterface(new CustomInterface() {
                    @Override
                    public void onClick(Context context) {
                     //   Toast.makeText(mContext, "切换到短信登录方式", Toast.LENGTH_SHORT).show();
                       // Intent pIntent = new Intent(mActivity, MessageActivity.class);
                       // mActivity.startActivityForResult(pIntent, 1002);
                        mAuthHelper.quitLoginPage();
                    }
                })
                .build());
        mAuthHelper.addAuthRegisterXmlConfig(new AuthRegisterXmlConfig.Builder()
                .setLayout(R.layout.custom_port_dialog_action_bar, new AbstractPnsViewDelegate() {
                    @Override
                    public void onViewCreated(View view) {
                        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mAuthHelper.quitLoginPage();
                            }
                        });
                    }
                })
                .build());

        mAuthHelper.setAuthUIConfig(new AuthUIConfig.Builder()

              /*  .setAppPrivacyOne("《隐私协议》", "https://www.baidu.com")*/

                .setAppPrivacyColor(Color.GRAY, Color.parseColor("#002E00"))


                .setNavHidden(true)
                .setSwitchAccHidden(true)
                .setPrivacyState(false)
                .setCheckboxHidden(true)

                .setLogoImgPath("mytel_app_launcher")
                .setLogoOffsetY(0)
                .setLogoWidth(42)
                .setLogoHeight(42)

                .setNumFieldOffsetY(unit + 10)
                .setNumberSize(22)


                // .setSloganText("为了您的账号安全，请先绑定手机号")
                .setSloganOffsetY(unit * 3)
                .setSloganTextSize(11)


                .setLogBtnBackgroundPath("shape_oauth_btn_bg")
                .setLogBtnOffsetY(unit * 4)
                .setLogBtnHeight(logBtnHeight)
                .setLogBtnMarginLeftAndRight(30)
                .setLogBtnTextSize(16)
                .setLogBtnText("本机号码一键登录")
                .setLogBtnWidth(190)
                .setLogBtnHeight(38)
                .setLogBtnTextColor(ColorUtil.BLACK)




                .setPageBackgroundPath("dialog_page_background")
                .setAuthPageActIn("in_activity", "out_activity")
                .setAuthPageActOut("in_activity", "out_activity")
                .setVendorPrivacyPrefix("《")
                .setVendorPrivacySuffix("》")
                .setAppPrivacyColor(Color.BLACK, ColorUtil.STRING_THEME)
                .setWebViewStatusBarColor(ColorUtil.THEME)
                .setWebNavColor(ColorUtil.STRING_THEME)
                .setDialogHeight(dialogHeight)
                .setDialogBottom(true)
                .setScreenOrientation(authPageOrientation)
                .create());


    }
}
