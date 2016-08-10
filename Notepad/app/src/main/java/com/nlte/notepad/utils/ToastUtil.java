package com.nlte.notepad.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 *
 * @author NLTE
 * @date 2016/7/8 0008 2:10
 */
public class ToastUtil {
    public static void showMsg(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
