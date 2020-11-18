package com.example.module_base.base

/**
 * @name td_horoscope
 * @class name：com.example.module_base.base
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/10/28 16:05
 * @class describe
 */
open class BasePresent<T> {

    protected val mCallbacks: ArrayList<T> = ArrayList()

    open fun registerCallback(callback: T) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback)
        }
    }

    open fun unregisterCallback(callback: T) {
        mCallbacks.remove(callback)
    }




}