package com.twx.module_usercenter.present;


import com.twx.module_usercenter.base.IBasePresent;
import com.twx.module_usercenter.view.ILogoutCallback;

public interface ILogoutPresent extends IBasePresent<ILogoutCallback> {

    void toLogout(String id);
}
