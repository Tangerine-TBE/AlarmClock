package com.example.module_usercenter.present;


import com.example.module_usercenter.base.IBasePresent;
import com.example.module_usercenter.view.IRegisterCallback;

import java.util.Map;

public interface IRegisterPresent extends IBasePresent<IRegisterCallback> {

    void getVerificationCode(String phoneNumber);

    void registerNumber(Map<String, String> userInfo);



}
