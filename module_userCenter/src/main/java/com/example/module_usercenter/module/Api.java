package com.example.module_usercenter.module;


import com.example.module_usercenter.bean.LoginBean;
import com.example.module_usercenter.bean.OauthBean;
import com.example.module_usercenter.bean.RegisterBean;
import com.example.module_usercenter.bean.ThirdlyRegisterBean;
import com.example.module_usercenter.bean.WeiXinBean;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


public interface Api {

    @POST("/api.php")
    Call<RegisterBean> getVerCode(@QueryMap Map<String, Object> params);

    @POST("/api.php")
    Call<RegisterBean> toRegister(@QueryMap Map<String, Object> params);

    @POST("/api.php")
    Call<LoginBean> toLogin(@QueryMap Map<String, Object> params);

    @POST("/api.php")
    Call<RegisterBean> toFindPwd(@QueryMap Map<String, Object> params);

    @POST("/api.php")
    Call<LoginBean> toThirdlyLogin(@QueryMap Map<String, Object> params);

    @POST("/api.php")
    Call<ThirdlyRegisterBean> toThirdlyRegister(@QueryMap Map<String, Object> params);

    @POST("/api.php")
    Call<RegisterBean> checkRegister(@QueryMap Map<String, Object> params);

    @POST("/api.php")
    Call<RegisterBean> toLogout(@QueryMap Map<String, Object> params);

    @POST("/api.php")
    Call<OauthBean> getNumber(@QueryMap Map<String, Object> params);


    @GET("access_token")
    Call<WeiXinBean> toWxAccredit(@QueryMap Map<String, String> params);

}
