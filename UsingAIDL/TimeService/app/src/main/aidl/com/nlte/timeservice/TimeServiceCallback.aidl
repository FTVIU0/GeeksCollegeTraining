// TimeServiceCallback.aidl
package com.nlte.timeservice;

// Declare any non-default types here with import statements

interface TimeServiceCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    //设置数据回调接口
    void onTimer(int numIndex);
}
