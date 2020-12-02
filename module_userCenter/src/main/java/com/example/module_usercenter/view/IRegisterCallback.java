package com.example.module_usercenter.view;


import com.example.module_usercenter.base.IBaseCallback;
import com.example.module_usercenter.bean.RegisterBean;

public interface IRegisterCallback extends IBaseCallback {

    void onLoadCode(RegisterBean bean);

    void onRegisterSuccess(RegisterBean registerBean);

}
