package com.example.module_usercenter.present;




import com.example.module_usercenter.base.IBasePresent;
import com.example.module_usercenter.view.ILoginCallback;

import java.util.Map;

public interface ILoginPresent extends IBasePresent<ILoginCallback> {

    void toLogin(Map<String, String> userInfo);

    void getOauthNumber(String number);

}
