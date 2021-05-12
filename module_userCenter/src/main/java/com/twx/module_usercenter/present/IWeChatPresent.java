package com.twx.module_usercenter.present;

import com.twx.module_usercenter.base.IBasePresent;
import com.twx.module_usercenter.view.IWeChatCallback;

import java.util.Map;

public interface IWeChatPresent extends IBasePresent<IWeChatCallback> {

    void toWxAccredit(Map<String, String> map);

    void checkWxRegister(Map<String, String> userInfo);

    void toWxRegister(Map<String, String> map);

    void toWxLogin(Map<String, String> map);
}
