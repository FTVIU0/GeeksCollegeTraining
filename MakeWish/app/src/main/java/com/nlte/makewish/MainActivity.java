package com.nlte.makewish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtnInputMsg;
    private TextView mTvWish;
    private TextView mTvShow;
    private static final int REQUEST_CODE =  1;//Intent请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化界面
        initView();

        mBtnInputMsg.setOnClickListener(this);

    }

    private void initView() {
        mBtnInputMsg = (Button) findViewById(R.id.btn_input_msg);
        mTvWish = (TextView) findViewById(R.id.tv_wish);
        mTvShow = (TextView) findViewById(R.id.tv_show);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, InputMsgActivity.class);

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == InputMsgActivity.RESULT_CODE) {
            mTvShow.setVisibility(View.VISIBLE);
            mTvWish.setText(data.getStringExtra(InputMsgActivity.WISH_MSG));
        } else if (resultCode == InputMsgActivity.CANCEL_CODE) {
            mTvWish.setText("");
            mTvShow.setVisibility(View.INVISIBLE);
            Toast.makeText(MainActivity.this, getString(R.string.toast_cancel), Toast.LENGTH_SHORT).show();

        }
    }
}
