package com.twx.module_usercenter.view;


import com.twx.module_usercenter.base.IBaseCallback;
import com.twx.module_usercenter.bean.RegisterBean;

public interface IFindPwdCallback extends IBaseCallback {

    void onLoadCode(RegisterBean bean);

    void onFindPwdSuccess(RegisterBean registerBean);

}
