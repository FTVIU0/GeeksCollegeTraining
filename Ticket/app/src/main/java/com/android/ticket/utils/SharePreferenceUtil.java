package com.android.ticket.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreference工具类
 * Created by Nlte
 * 2016/4/18 0018.
 */
public class SharePreferenceUtil {
	public final static String SHAREPREFERENCE_NAME = "config";
    //Boolean
    public static void putBoolean(Context context, String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHAREPREFERENCE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }
    public static boolean getBoolean(Context context, String key, boolean defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHAREPREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }

    //String
    public static void putString(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHAREPREFERENCE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }
    public static String getString(Context context, String key, String defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHAREPREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }

    //int
    public static void putInt(Context context, String key, int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHAREPREFERENCE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).apply();
    }
    public static int getInt(Context context, String key, int defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHAREPREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defValue);
    }
}
