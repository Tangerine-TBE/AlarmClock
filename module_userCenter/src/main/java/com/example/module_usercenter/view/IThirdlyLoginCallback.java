package com.example.module_usercenter.view;
import com.example.module_usercenter.base.IBaseCallback;
import com.example.module_usercenter.bean.LoginBean;
import com.example.module_usercenter.bean.RegisterBean;
import com.example.module_usercenter.bean.ThirdlyRegisterBean;

public interface IThirdlyLoginCallback extends IBaseCallback {

      void onThirdlyLoginSuccess(LoginBean bean);

      void onThirdlyLoginError();


      void onCheckThirdlyRegisterSuccess(RegisterBean bean);

      void onCheckError();


      void onThirdlyRegisterSuccess(ThirdlyRegisterBean bean);

      void onThirdlyRegisterError();
}
