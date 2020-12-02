package com.example.module_usercenter.bean

/**
 * @name td_horoscope
 * @class name：com.example.module_usercenter.bean
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/5 14:26
 * @class describe
 */
data class OauthBean(
    val `data`: Data,
    val msg: String,
    val ret: Int
)

data class Data(
    val code: Int,
    val exsit: Boolean,
    val mobile: String,
    val passwd: String
)