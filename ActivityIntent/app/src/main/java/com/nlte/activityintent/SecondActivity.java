package com.nlte.activityintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //获取控件
        TextView tvShowMsg = (TextView) findViewById(R.id.tv_show_msg);

        //获取FirstActivity传递过来的数据
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra(FirstActivity.INPUT_MSG);

        //将数据设置给TextView
        tvShowMsg.setText(stringExtra);
    }
}
