package com.example.module_usercenter.present.impl;


import com.example.module_base.util.LogUtils;
import com.example.module_usercenter.bean.LoginBean;
import com.example.module_usercenter.bean.OauthBean;
import com.example.module_usercenter.module.UserData;
import com.example.module_usercenter.present.ILoginPresent;
import com.example.module_usercenter.view.ILoginCallback;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresentImpl implements ILoginPresent {
    private static LoginPresentImpl  sInstance;
    private final UserData mUserData;

    public static LoginPresentImpl getInstance() {
        if (sInstance == null) {
            sInstance = new LoginPresentImpl();
        }
        return sInstance;
    }
     private LoginPresentImpl() {
         mUserData = UserData.getInstance();

    }


    @Override
    public void toLogin(Map<String, String> userInfo) {
        handlerLoading();
        mUserData.doLogin(userInfo, new Callback<LoginBean>() {
                private LoginBean mBody;
                @Override
                public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {

                    if (response.code()== HttpURLConnection.HTTP_OK) {
                        mBody = response.body();
                        if (mBody != null) {
                            for (ILoginCallback callback : mCallbacks) {
                                callback.onLoginSuccess(mBody);
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginBean> call, Throwable t) {

                    for (ILoginCallback callback : mCallbacks) {
                        callback.onLoginError();
                    }
                }
            });

    }


    @Override
    public void getOauthNumber(String number) {
        mUserData.doPhoneNumber(number, new Callback<OauthBean>() {
            @Override
            public void onResponse(Call<OauthBean> call, Response<OauthBean> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    OauthBean body = response.body();
                    if (body != null) {
                        for (ILoginCallback callback : mCallbacks) {
                            callback.onNumberSuccess(body);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<OauthBean> call, Throwable t) {

            }
        });
    }


    private void handlerLoading() {
        for (ILoginCallback callback : mCallbacks) {
            callback.onLoading();
        }
    }

    private List<ILoginCallback> mCallbacks=new ArrayList<>();

    @Override
    public void registerCallback(ILoginCallback iLoginCallback) {
        if (!mCallbacks.contains(iLoginCallback)) {
            mCallbacks.add(iLoginCallback);
        }
    }

    @Override
    public void unregisterCallback(ILoginCallback iLoginCallback) {
            mCallbacks.remove(iLoginCallback);
    }
}
