package com.example.module_usercenter.present;


import com.example.module_usercenter.base.IBasePresent;
import com.example.module_usercenter.view.ILogoutCallback;

public interface ILogoutPresent extends IBasePresent<ILogoutCallback> {

    void toLogout(String id);
}
