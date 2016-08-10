package com.nlte.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

    private TextView mTvCompass;
    private TextView mTvDegrees;
    private CompassView mIvCompass;
    private Sensor mOrientationSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mTvCompass = (TextView) findViewById(R.id.tvCompass);
        mTvDegrees = (TextView) findViewById(R.id.tvDegrees);
        mIvCompass = (CompassView) findViewById(R.id.ivCompass);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mOrientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //添加监听
        mSensorManager.registerListener(this, mOrientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注销监听
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ORIENTATION:
                float degress = sensorEvent.values[0];
                updateTv(degress);
                mIvCompass.updateDirection(-degress);
                break;
            default:
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    private void updateTv(float dg) {
        mTvDegrees.setText(String.valueOf((int)dg));
        if (dg > 20 && dg < 70) {
            mTvCompass.setText(R.string.northeast);
        } else if (dg > 110 && dg < 160) {
            mTvCompass.setText(R.string.southeast);
        } else if (dg > 200 && dg < 250) {
            mTvCompass.setText(R.string.southwest);
        } else if (dg > 290 && dg < 340) {
            mTvCompass.setText(R.string.northwest);
        } else if (dg > 0 && dg < 20 || dg > 340 && dg <= 360) {
            mTvCompass.setText(R.string.north);
        } else if (dg > 70 && dg < 110) {
            mTvCompass.setText(R.string.east);
        } else if (dg > 160 && dg < 200) {
            mTvCompass.setText(R.string.south);
        } else if (dg > 250 && dg < 290) {
            mTvCompass.setText(R.string.west);
        }

    }
}
