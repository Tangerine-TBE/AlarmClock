package com.example.module_usercenter.present;




import com.example.module_usercenter.base.IBasePresent;
import com.example.module_usercenter.view.IFindPwdCallback;

import java.util.Map;

public interface IFindPwdPresent extends IBasePresent<IFindPwdCallback> {

    void getVerificationCode(String phoneNumber);

    void findPwd(Map<String, String> userInfo);

}
