package com.example.module_usercenter.present.impl;



import com.example.module_usercenter.bean.LoginBean;
import com.example.module_usercenter.bean.RegisterBean;
import com.example.module_usercenter.bean.ThirdlyRegisterBean;
import com.example.module_usercenter.bean.WeiXinBean;
import com.example.module_usercenter.module.UserData;
import com.example.module_usercenter.present.IWeChatPresent;
import com.example.module_usercenter.view.IWeChatCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeChatPresentImpl implements IWeChatPresent {

    private static WeChatPresentImpl  sInstance;
    private final UserData mUserData;

    public static WeChatPresentImpl getInstance() {
        if (sInstance == null) {
            sInstance = new WeChatPresentImpl();
        }
        return sInstance;
    }
     private WeChatPresentImpl() {
         mUserData = UserData.getInstance();
     }


    @Override
    public void toWxAccredit(Map<String, String> map) {
        mUserData.doWxAccredit(map, new Callback<WeiXinBean>() {
            @Override
            public void onResponse(Call<WeiXinBean> call, Response<WeiXinBean> response) {
                if (response.code()== HttpURLConnection.HTTP_OK) {
                    WeiXinBean body = response.body();
                    if (body != null) {
                        for (IWeChatCallback callback : mCallbacks) {
                            callback.onWxAccreditSuccess(body);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<WeiXinBean> call, Throwable t) {

                for (IWeChatCallback callback : mCallbacks) {
                    callback.onWxAccreditError();
                }
            }
        });
    }

    @Override
    public void checkWxRegister(Map<String, String> userInfo) {
        mUserData.doCheckRegister(userInfo, new Callback<RegisterBean>() {
            @Override
            public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                if (response.code()== HttpURLConnection.HTTP_OK) {
                    RegisterBean body = response.body();
                    if (body != null) {
                        for (IWeChatCallback callback : mCallbacks) {
                            callback.onCheckWxRegisterSuccess(body);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<RegisterBean> call, Throwable t) {
                for (IWeChatCallback callback : mCallbacks) {
                    callback.onCheckWxError();
                }
            }
        });
    }

    @Override
    public void toWxRegister(Map<String, String> map) {
        mUserData.doThirdlyRegister(map, new Callback<ThirdlyRegisterBean>() {
            @Override
            public void onResponse(Call<ThirdlyRegisterBean> call, Response<ThirdlyRegisterBean> response) {
                if (response.code()== HttpURLConnection.HTTP_OK) {
                    ThirdlyRegisterBean body = response.body();
                    for (IWeChatCallback callback : mCallbacks) {
                        callback.onWxRegisterSuccess(body);
                    }
                }
            }

            @Override
            public void onFailure(Call<ThirdlyRegisterBean> call, Throwable t) {
                for (IWeChatCallback callback : mCallbacks) {
                    callback.onWxRegisterError();
                }
            }
        });

    }

    @Override
    public void toWxLogin(Map<String, String> map) {
        mUserData.doThirdlyLogin(map, new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (response.code()== HttpURLConnection.HTTP_OK) {
                    LoginBean body = response.body();
                    if (body != null) {
                        for (IWeChatCallback callback : mCallbacks) {
                            callback.onWxLoginSuccess(body);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                for (IWeChatCallback callback : mCallbacks) {
                    callback.onWxLoginError();
                }
            }
        });
    }

    private List<IWeChatCallback> mCallbacks=new ArrayList<>();

    @Override
    public void registerCallback(IWeChatCallback iWeChatCallback) {
        if (!mCallbacks.contains(iWeChatCallback)) {
            mCallbacks.add(iWeChatCallback);
        }
    }

    @Override
    public void unregisterCallback(IWeChatCallback iWeChatCallback) {
        mCallbacks.remove(iWeChatCallback);
    }
}
