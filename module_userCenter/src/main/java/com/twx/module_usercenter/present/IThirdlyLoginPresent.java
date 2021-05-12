package com.twx.module_usercenter.present;


import com.twx.module_usercenter.base.IBasePresent;
import com.twx.module_usercenter.view.IThirdlyLoginCallback;
import java.util.Map;

public interface IThirdlyLoginPresent extends IBasePresent<IThirdlyLoginCallback> {

    void toThirdlyRegister(Map<String, String> userInfo);

    void checkRegister(Map<String, String> userInfo);

    void toThirdlyLogin(Map<String, String> userInfo);



}
