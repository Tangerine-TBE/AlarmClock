package com.example.module_usercenter.base;

public interface IBasePresent<T> {

    void registerCallback(T t);

    void unregisterCallback(T t);

}
