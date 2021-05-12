package com.twx.module_usercenter.view;


import com.twx.module_usercenter.base.IBaseCallback;
import com.twx.module_usercenter.bean.RegisterBean;

public interface ILogoutCallback extends IBaseCallback {

    void onLogoutLoading();

    void onLogoutSuccess(RegisterBean registerBean);

    void onLogoutError();
}
