// ITimeRemoteService.aidl
package com.nlte.timeservice;

// Declare any non-default types here with import statements

import com.nlte.timeservice.TimeServiceCallback;

interface ITimeRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    //向外提供注册服务接口
    void registService(TimeServiceCallback callback);

    //向外提供注销服务接口
    void unRegistService(TimeServiceCallback callback);
}
