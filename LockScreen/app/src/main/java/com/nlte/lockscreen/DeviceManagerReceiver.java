package com.nlte.lockscreen;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * NLTE on 2016/7/24 0024 02:55
 */
public class DeviceManagerReceiver extends DeviceAdminReceiver {
    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        showToast(context, context.getString(R.string.toast_unreg));
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        showToast(context, context.getString(R.string.toast_reg));
    }
    private void showToast(Context context, String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();

    }
}
