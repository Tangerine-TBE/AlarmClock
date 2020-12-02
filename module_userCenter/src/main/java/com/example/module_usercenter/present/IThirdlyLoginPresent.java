package com.example.module_usercenter.present;


import com.example.module_usercenter.base.IBasePresent;
import com.example.module_usercenter.view.IThirdlyLoginCallback;
import java.util.Map;

public interface IThirdlyLoginPresent extends IBasePresent<IThirdlyLoginCallback> {

    void toThirdlyRegister(Map<String, String> userInfo);

    void checkRegister(Map<String, String> userInfo);

    void toThirdlyLogin(Map<String, String> userInfo);



}
