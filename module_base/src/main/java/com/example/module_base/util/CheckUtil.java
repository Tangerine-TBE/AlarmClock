package com.example.module_base.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * @author wujinming QQ:1245074510
 * @name td_horoscope
 * @class name：com.example.module_base.util
 * @class describe
 * @time 2020/11/3 13:49
 * @class describe
 */
public class CheckUtil {
    public static Boolean validateName(String name) {
        return name.matches("^([\\u4e00-\\u9fa5]{1,20}|[a-zA-Z\\.\\s]{1,20})$");
    }

}
