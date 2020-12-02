package com.example.module_usercenter.view;


import com.example.module_usercenter.base.IBaseCallback;
import com.example.module_usercenter.bean.LoginBean;
import com.example.module_usercenter.bean.OauthBean;

public interface ILoginCallback extends IBaseCallback {

    void onLoginSuccess(LoginBean loginBean);


    void onNumberSuccess(OauthBean oauthBean);

    void onLoginError();
}
