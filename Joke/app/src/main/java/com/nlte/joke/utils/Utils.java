package com.nlte.joke.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;

/**
 * NLTE on 2016/8/5 0005 22:21
 */
public class Utils {
    private static final String sBaseUri = "http://nlteblog.duapp.com/wp-latestposts.php?";//获取文字列表URL
    private static Toast sToast;

    /**
     * Toast封装  防止多次显示
     * @param context 上下文
     * @param content 显示的内容
     */
    public static void showToast(Context context, String content){
        if (sToast == null){
            sToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        }else {
            sToast.setText(content);
        }
        sToast.show();
    }

    /**
     * 获取Url
     * @param page 页码
     * @return
     */
    public static String getUrl(int page) {

        int offset = (page - 1) * 15; //将页数转换为相对0的偏移值
        return Uri.parse(sBaseUri).buildUpon()
                .appendQueryParameter("offset", String.valueOf(offset))
                .build().toString();
    }

    /**
     * 检查网络是否可用
     * @param activity
     * @return true 表示可用 false 表示不可用
     */
    public static boolean isNetworkAvailable(Activity activity){
        Context context = activity.getApplicationContext();
        
        //获取手机连接管理对象（包括wifi，net等连接的管理）
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if (connectivityManager == null){
            return false;
        }else {
            //获取Network 对象
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            
            if (networkInfos != null && networkInfos.length > 0){
                for (int i = 0; i < networkInfos.length; i++) {
                    if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
