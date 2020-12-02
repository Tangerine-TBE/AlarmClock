package com.example.module_usercenter.present.impl;



import com.example.module_usercenter.bean.RegisterBean;
import com.example.module_usercenter.module.UserData;
import com.example.module_usercenter.present.IFindPwdPresent;
import com.example.module_usercenter.view.IFindPwdCallback;

import java.net.HttpURLConnection;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindPwdPresentImpl implements IFindPwdPresent {
    private IFindPwdCallback mIFindPwdCallback=null;

    private static FindPwdPresentImpl  sInstance;
    private final UserData mUserData;

    public static FindPwdPresentImpl getInstance() {
        if (sInstance == null) {
            sInstance = new FindPwdPresentImpl();
        }
        return sInstance;
    }
     private FindPwdPresentImpl() {
         mUserData = UserData.getInstance();

     }


    @Override
    public void getVerificationCode(String phoneNumber) {
        mUserData.doCode(phoneNumber, new Callback<RegisterBean>() {
            @Override
            public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                if (response.code()== HttpURLConnection.HTTP_OK) {
                    RegisterBean body = response.body();
                    if (body != null) {
                        if (mIFindPwdCallback != null) {
                            mIFindPwdCallback.onLoadCode(body);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void findPwd(Map<String, String> userInfo) {
        handlerLoading();
        mUserData.doFindPwd(userInfo, new Callback<RegisterBean>() {
            @Override
            public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                if (response.code()== HttpURLConnection.HTTP_OK) {
                    RegisterBean body = response.body();
                    if (body != null) {
                        handlerSuccess(body);
                    }

                }
            }
            @Override
            public void onFailure(Call<RegisterBean> call, Throwable t) {
                if (mIFindPwdCallback != null) {
                    mIFindPwdCallback.onError();
                }

            }
        });
    }

    private void handlerSuccess(RegisterBean body) {
        if (mIFindPwdCallback != null) {
            mIFindPwdCallback.onFindPwdSuccess(body);
        }
    }

    private void handlerLoading() {
        if (mIFindPwdCallback != null) {
            mIFindPwdCallback.onLoading();
        }
    }

    @Override
    public void registerCallback(IFindPwdCallback iFindPwdCallback) {
        this.mIFindPwdCallback=iFindPwdCallback;
    }

    @Override
    public void unregisterCallback(IFindPwdCallback iFindPwdCallback) {
        if (mIFindPwdCallback != null) {
            mIFindPwdCallback=null;
        }
    }
}
