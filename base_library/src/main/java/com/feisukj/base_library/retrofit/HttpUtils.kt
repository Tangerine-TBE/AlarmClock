package com.feisukj.base_library.retrofit

import androidx.annotation.NonNull
import com.feisukj.base_library.utils.BaseConstant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Author : Gupingping
 * Date : 2019/1/17
 * QQ : 464955343
 */
class HttpUtils private constructor() {
    private val retrofitForFeisu: Retrofit
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(host)
                .build()
    }
    val logging = LoggingInterceptor(Logger())
    val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .connectTimeout(9, TimeUnit.SECONDS)
            .build()


    init {
        logging.level = LoggingInterceptor.Level.BODY
        val okHttpClientForAD = OkHttpClient().newBuilder()
                .addInterceptor(logging)
                .connectTimeout(9, TimeUnit.SECONDS)
                .build()
        retrofitForFeisu = Retrofit.Builder()
                .client(okHttpClientForAD)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BaseConstant.FeiSuHost)
                .build()

    }

    private object SingletonHolder {
        internal var INSTANCE = HttpUtils()
    }

    companion object {
        var host: String= BaseConstant.FeiSuHost

        private val instance: HttpUtils
            get() = SingletonHolder.INSTANCE

        /**
         * 用于设置动态host
         */
        fun <T> setService(@NonNull host: String, clazz: Class<T>): T {
            this.host = host
            return HttpUtils.instance.retrofit.create(clazz)
        }

        /**
         * 用于广告配置请求
         */
        fun <T> setServiceForFeisuConfig(clazz: Class<T>): T {
            return HttpUtils.instance.retrofitForFeisu.create(clazz)
        }
    }

}
