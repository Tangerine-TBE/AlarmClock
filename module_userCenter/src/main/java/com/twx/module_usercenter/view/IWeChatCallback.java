package com.twx.module_usercenter.view;


import com.twx.module_usercenter.base.IBaseCallback;
import com.twx.module_usercenter.bean.LoginBean;
import com.twx.module_usercenter.bean.RegisterBean;
import com.twx.module_usercenter.bean.ThirdlyRegisterBean;
import com.twx.module_usercenter.bean.WeiXinBean;

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
