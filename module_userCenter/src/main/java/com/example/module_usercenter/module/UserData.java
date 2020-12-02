package com.example.module_usercenter.module;


import com.example.module_usercenter.bean.LoginBean;
import com.example.module_usercenter.bean.OauthBean;
import com.example.module_usercenter.bean.RegisterBean;
import com.example.module_usercenter.bean.ThirdlyRegisterBean;
import com.example.module_usercenter.bean.WeiXinBean;
import com.example.module_usercenter.utils.ApiMapUtil;
import com.example.module_usercenter.utils.Contents;
import com.example.module_usercenter.utils.Md5Util;
import com.example.module_usercenter.utils.RetrofitManager;
import com.example.module_usercenter.utils.SortMapUtil;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class UserData {

    private final Api mApi;
    private static UserData sInstance;
    private final Api mMWeixin;

    public static UserData getInstance() {
        if (sInstance == null) {
            sInstance = new UserData();
        }
        return sInstance;
    }
     private UserData() {
         Retrofit retrofitUser = RetrofitManager.getInstance().getRetrofitUser();
         mApi = retrofitUser.create(Api.class);

         Retrofit retrofitWeiXin = RetrofitManager.getInstance().getMRetrofitWeiXin();
         mMWeixin = retrofitWeiXin.create(Api.class);

     }

    public void doCode(String phoneNumber, Callback<RegisterBean> callback) {
        //获取随机数
        int random = new Random().nextInt();
        //获取时间戳
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map1=new TreeMap<>();
        map1.put(Contents.PACKAGE,Contents.APP_PACKAGE);
        map1.put(Contents.MOBILE,phoneNumber);
        String value1 = SortMapUtil.sortMapByValue(map1);
        //Md5值
        String checkCode = Md5Util.md5(Contents.TOKEN+currentTimeMillis+random+Contents.GET_CODE+value1);
        Map<String, Object> map = ApiMapUtil.setMapValues(Contents.GET_CODE, currentTimeMillis, random, checkCode, map1);
        mApi.getVerCode(map).enqueue(callback);

    }


     public void doRegister(Map<String, String> map, Callback<RegisterBean> callback) {
         //获取随机数
         int random = new Random().nextInt();
         //获取时间戳
         long currentTimeMillis = System.currentTimeMillis();

         String value = SortMapUtil.sortMapByValue(map);

         String checkCode = Md5Util.md5(Contents.TOKEN+currentTimeMillis+random+ Contents.ADD_USER+value);
         Map<String, Object> map1= ApiMapUtil.setMapValues(Contents.ADD_USER, currentTimeMillis, random, checkCode, map);
        mApi.toRegister(map1).enqueue(callback);

     }

     public void doLogin(Map<String, String> userInfo, Callback<LoginBean> callback) {
         long currentTimeMillis = System.currentTimeMillis();
         int random = new Random().nextInt();
         String sortMapByValue = SortMapUtil.sortMapByValue(userInfo);
         String md5 = Md5Util.md5(Contents.TOKEN + currentTimeMillis + random + Contents.LOGIN+sortMapByValue);
         Map<String, Object> stringObjectMap = ApiMapUtil.setMapValues(Contents.LOGIN,currentTimeMillis, random, md5,userInfo);
         mApi.toLogin(stringObjectMap).enqueue(callback);
     }


     public void doFindPwd(Map<String, String> userInfo, Callback<RegisterBean> callback) {
         String sortMapByValue = SortMapUtil.sortMapByValue(userInfo);
         long currentTimeMillis = System.currentTimeMillis();
         int random = new Random().nextInt();
         String md5 = Md5Util.md5(Contents.TOKEN + currentTimeMillis + random + Contents.FIND_PWD+sortMapByValue);
         Map<String, Object> stringObjectMap = ApiMapUtil.setMapValues(Contents.FIND_PWD,currentTimeMillis, random, md5,userInfo);
         mApi.toFindPwd(stringObjectMap).enqueue(callback);
     }


    public void doThirdlyLogin(Map<String, String> userInfo, Callback<LoginBean> callback) {
        String sortMapByValue = SortMapUtil.sortMapByValue(userInfo);
        long currentTimeMillis = System.currentTimeMillis();
        int random = new Random().nextInt();
        String md5 = Md5Util.md5(Contents.TOKEN + currentTimeMillis + random + Contents.LOGIN_THIRD+sortMapByValue);
        Map<String, Object> stringObjectMap = ApiMapUtil.setMapValues(Contents.LOGIN_THIRD,currentTimeMillis, random, md5,userInfo);
        mApi.toThirdlyLogin(stringObjectMap).enqueue(callback);
    }

    public void doThirdlyRegister(Map<String, String> userInfo, Callback<ThirdlyRegisterBean> callback) {
        Map<String, Object> stringObjectMap = getThirdlyInfo(userInfo, Contents.REGISTER_BY_THIRD);
        mApi.toThirdlyRegister(stringObjectMap).enqueue(callback);
    }

    private Map<String, Object> getThirdlyInfo(Map<String, String> userInfo, String registerByThird) {
        String sortMapByValue = SortMapUtil.sortMapByValue(userInfo);
        long currentTimeMillis = System.currentTimeMillis();
        int random = new Random().nextInt();
        String md5 = Md5Util.md5(Contents.TOKEN + currentTimeMillis + random + registerByThird + sortMapByValue);
        return ApiMapUtil.setMapValues(registerByThird, currentTimeMillis, random, md5, userInfo);
    }


    public void doCheckRegister(Map<String, String> userInfo, Callback<RegisterBean> callback) {
        String sortMapByValue = SortMapUtil.sortMapByValue(userInfo);
        long currentTimeMillis = System.currentTimeMillis();
        int random = new Random().nextInt();
        String md5 = Md5Util.md5(Contents.TOKEN + currentTimeMillis + random + Contents.CHECK_THIRD+sortMapByValue);
        Map<String, Object> stringObjectMap = ApiMapUtil.setMapValues(Contents.CHECK_THIRD,currentTimeMillis, random, md5,userInfo);
        mApi.checkRegister(stringObjectMap).enqueue(callback);
    }

    public void doWxAccredit(Map<String, String> userInfo, Callback<WeiXinBean> callback) {
        mMWeixin.toWxAccredit(userInfo).enqueue(callback);
    }

    public void doLogout(String id, Callback<RegisterBean> callback) {
        long currentTimeMillis = System.currentTimeMillis();
        int random = new Random().nextInt();
        String md5 = Md5Util.md5(Contents.TOKEN + currentTimeMillis + random + Contents.DELETE_USER+id);
        Map<String, Object> setStringValues = ApiMapUtil.setStringValues(Contents.DELETE_USER,currentTimeMillis, random, md5,id);
        mApi.toLogout(setStringValues).enqueue(callback);
    }

    public void doPhoneNumber(String phone, Callback<OauthBean> callback) {
        long currentTimeMillis = System.currentTimeMillis();
        int random = new Random().nextInt();
        String md5 = Md5Util.md5(Contents.TOKEN + currentTimeMillis + random + Contents.GET_PHONE+phone);
        Map<String, Object> setStringValues = ApiMapUtil.setOauthValues(Contents.GET_PHONE,currentTimeMillis, random, md5,phone);
        mApi.getNumber(setStringValues).enqueue(callback);
    }



}
