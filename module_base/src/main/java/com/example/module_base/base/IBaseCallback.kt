package com.example.module_base.base

/**
 * @name td_horoscope
 * @class name：com.example.module_base.base
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/10/28 16:04
 * @class describe
 */
interface IBaseCallback {
    /**
     *  加载中
     */
    fun onLoading()

    /**
     * 网络错误
     */
    fun onError(t:String)

    /**
     * 内容为空
     */
    fun onEmpty()
}