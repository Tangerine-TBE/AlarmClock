package com.feisukj.base_library.retrofit;


import android.util.Log;

/**
 * Author : Gupingping
 * Date : 2018/9/28
 * QQ : 464955343
 */
public class Logger implements LoggingInterceptor.Logger {

    @Override
    public void log(String message) {
        Log.e("http---",message);
    }
}
