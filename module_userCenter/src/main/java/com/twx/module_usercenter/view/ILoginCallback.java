package com.twx.module_usercenter.view;


import com.twx.module_usercenter.base.IBaseCallback;
import com.twx.module_usercenter.bean.LoginBean;
import com.twx.module_usercenter.bean.OauthBean;

public interface ILoginCallback extends IBaseCallback {

    void onLoginSuccess(LoginBean loginBean);


    void onNumberSuccess(OauthBean oauthBean);

    void onLoginError();
}
