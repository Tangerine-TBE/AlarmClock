package com.example.module_usercenter.present.impl;


import com.example.module_base.util.LogUtils;
import com.example.module_usercenter.bean.RegisterBean;
import com.example.module_usercenter.module.UserData;
import com.example.module_usercenter.present.IRegisterPresent;
import com.example.module_usercenter.view.IRegisterCallback;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresentImpl implements IRegisterPresent {

    private static RegisterPresentImpl sInstance;
    private UserData mUserData;

    public static RegisterPresentImpl getInstance() {
        if (sInstance == null) {
            sInstance = new RegisterPresentImpl();
        }
        return sInstance;
    }

    private RegisterPresentImpl() {
        mUserData = UserData.getInstance();

    }

    //获取验证码
    @Override
    public void getVerificationCode(String phoneNumber) {
        mUserData.doCode(phoneNumber, new Callback<RegisterBean>() {
            @Override
            public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    RegisterBean body = response.body();
                    if (body != null) {
                        for (IRegisterCallback callback : mCallbacks) {
                            callback.onLoadCode(body);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterBean> call, Throwable t) {

            }
        });


    }

    //http://www.aisou.club/api.php?password=123456&mobile=18720282934&code=67385&platform=_360&package=com.chenxing.androidruler&timestamp=1594086842472&nonce=8451&signature=5a0606c02f38593be8f1d24818e6d085&service=passport.registerByMobile

    //注册
    @Override
    public void registerNumber(Map<String, String> userInfo) {
        handlerLoading();
        mUserData.doRegister(userInfo, new Callback<RegisterBean>() {
            @Override
            public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    RegisterBean body = response.body();
                    handlerSuccess(body);

                }
            }

            @Override
            public void onFailure(Call<RegisterBean> call, Throwable t) {
                handlerError();
            }
        });

    }



    private void handlerLoading() {
        for (IRegisterCallback callback : mCallbacks) {
            callback.onLoading();
        }
    }

    private void handlerError() {
        for (IRegisterCallback callback : mCallbacks) {
            callback.onError();
        }
    }

    private void handlerSuccess(RegisterBean body) {
        if (body != null) {
            for (IRegisterCallback callback : mCallbacks) {
                callback.onRegisterSuccess(body);
            }

        }
    }

    private List<IRegisterCallback> mCallbacks=new ArrayList<>();
    @Override
    public void registerCallback(IRegisterCallback iRegisterCallback) {
        if (!mCallbacks.contains(iRegisterCallback)) {
            mCallbacks.add(iRegisterCallback);
        }
    }

    @Override
    public void unregisterCallback(IRegisterCallback iRegisterCallback) {
        mCallbacks.remove(iRegisterCallback);
    }
}
