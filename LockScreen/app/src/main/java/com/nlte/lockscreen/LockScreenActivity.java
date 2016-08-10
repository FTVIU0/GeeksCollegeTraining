package com.nlte.lockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LockScreenActivity extends AppCompatActivity {
    private DevicePolicyManager mDevicePolicyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDevicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(this, DeviceManagerReceiver.class);

        if (mDevicePolicyManager.isAdminActive(componentName)){
            mDevicePolicyManager.lockNow();
            finish();
        }else {
            finish();
            startActivity(new Intent(this, ConfigureActivity.class));
        }

    }
}
