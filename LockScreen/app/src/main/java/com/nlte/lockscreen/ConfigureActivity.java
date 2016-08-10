package com.nlte.lockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ConfigureActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnRegDeviceAdmin;
    private Button mBtnUnregDeviceAdmin;
    private Button mBtnLockScreen;
    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mComponentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        mDevicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, DeviceManagerReceiver.class);

        initUi();

        mBtnRegDeviceAdmin.setOnClickListener(this);
        mBtnUnregDeviceAdmin.setOnClickListener(this);
        mBtnLockScreen.setOnClickListener(this);

    }

    private void initUi() {
        mBtnRegDeviceAdmin = (Button) findViewById(R.id.btnRegDeviceAdmin);
        mBtnUnregDeviceAdmin = (Button) findViewById(R.id.btnUnregDeviceAdmin);
        mBtnLockScreen = (Button) findViewById(R.id.btnLockScreen);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegDeviceAdmin:
                //注册设备管理器
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
                startActivity(intent);
                break;
            case R.id.btnUnregDeviceAdmin:
                //注销设备管理器
                mDevicePolicyManager.removeActiveAdmin(mComponentName);

                mBtnRegDeviceAdmin.setVisibility(View.VISIBLE);
                mBtnUnregDeviceAdmin.setVisibility(View.GONE);
                mBtnLockScreen.setVisibility(View.GONE);
                break;
            case R.id.btnLockScreen:
                //锁屏
                mDevicePolicyManager.lockNow();
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDevicePolicyManager.isAdminActive(mComponentName)){
            mBtnRegDeviceAdmin.setVisibility(View.GONE);
            mBtnUnregDeviceAdmin.setVisibility(View.VISIBLE);
            mBtnLockScreen.setVisibility(View.VISIBLE);
        }
    }
}
