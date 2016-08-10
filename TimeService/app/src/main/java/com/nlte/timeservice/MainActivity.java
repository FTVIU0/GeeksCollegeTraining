package com.nlte.timeservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 服务端
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private Button mBtnBindService;
    private Button mBtnUnbindService;
    private TextView mTvShow;
    private Intent mIntent;

    private ITimeRemoteService mTimeRemoteService = null;

    private static final String NUM_INDEX = "NUM_INDEX";//数据key

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //获取服务回调的结果并展示
            MainActivity _this = (MainActivity) msg.obj;
            int result = msg.getData().getInt(NUM_INDEX);
            _this.mTvShow.setText(getString(R.string.result_msg) + result);
        }
    };


    private TimeServiceCallback.Stub onServiceCallback = new TimeServiceCallback.Stub() {
        @Override
        public void onTimer(int numIndex) throws RemoteException {

            //利用handler发送Msg
            Message message = new Message();
            message.obj = MainActivity.this;

            Bundle bundle = new Bundle();
            bundle.putInt(NUM_INDEX, numIndex);

            message.setData(bundle);
            mHandler.sendMessage(message);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIntent = new Intent(this, TimeService.class);

        initView();

        //控件添加点击事件
        mBtnBindService.setOnClickListener(this);
        mBtnUnbindService.setOnClickListener(this);
    }

    private void initView() {
        mBtnBindService = (Button) findViewById(R.id.btn_bind_service);
        mBtnUnbindService = (Button) findViewById(R.id.btn_unbind_service);
        mTvShow = (TextView) findViewById(R.id.tv_show);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bind_service:
                if (mTimeRemoteService == null) {
                    bindService(mIntent, this, BIND_AUTO_CREATE);
                }
                break;
            case R.id.btn_unbind_service:
                if (mTimeRemoteService != null) {
                    unbindService(this);
                    callbackUnRegistService();
                    mTimeRemoteService = null;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mTimeRemoteService = ITimeRemoteService.Stub.asInterface(service);

        callbackRegistService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        callbackUnRegistService();
    }

    //注销服务
    private void callbackUnRegistService() {
        try {
            mTimeRemoteService.unRegistService(onServiceCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //注册服务
    private void callbackRegistService() {
        try {
            mTimeRemoteService.registService(onServiceCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimeRemoteService != null) {
            //解绑并注销服务
            unbindService(this);
            callbackUnRegistService();
        }
    }
}