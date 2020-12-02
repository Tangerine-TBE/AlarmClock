package com.example.module_usercenter.view;


import com.example.module_usercenter.base.IBaseCallback;
import com.example.module_usercenter.bean.LoginBean;
import com.example.module_usercenter.bean.RegisterBean;
import com.example.module_usercenter.bean.ThirdlyRegisterBean;
import com.example.module_usercenter.bean.WeiXinBean;

public interface IWeChatCallback extends IBaseCallback {

  void onWxAccreditSuccess(WeiXinBean weiXinBean);

  void onWxAccreditError();

  void onCheckWxRegisterSuccess(RegisterBean bean);

  void onCheckWxError();

  void onWxRegisterSuccess(ThirdlyRegisterBean thirdlyRegisterBean);

  void onWxRegisterError();

  void onWxLoginSuccess(LoginBean loginBean);

  void onWxLoginError();


}
