package com.example.module_usercenter.utils;



import com.example.module_base.BuildConfig;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {





    private static RetrofitManager sInstance;


    public Retrofit getRetrofitUser() {
        return mRetrofitUser;
    }
    private final Retrofit mRetrofitUser;

    public Retrofit getMRetrofitWeiXin() {
        return mMRetrofitWeiXin;
    }

    private final Retrofit mMRetrofitWeiXin;

    public static RetrofitManager getInstance() {
        if (sInstance == null) {
            sInstance = new RetrofitManager();
        }
        return sInstance;
    }

    private RetrofitManager() {

        mRetrofitUser = new Retrofit.Builder()
                .baseUrl(Contents.USER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient().build())
                .build();

        mMRetrofitWeiXin = new Retrofit.Builder()
                .baseUrl(Contents.WECHAT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient().build())
                .build();
    }


    private OkHttpClient.Builder getClient(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        //add log record
        if (BuildConfig.DEBUG) {
            //打印网络请求日志
            LoggingInterceptor httpLoggingInterceptor = new LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BASIC)
                    .log(Platform.INFO)
                    .request("请求")
                    .response("响应")
                    .build();
            httpClientBuilder.addInterceptor(httpLoggingInterceptor);
        }
        return httpClientBuilder;
    }
}
