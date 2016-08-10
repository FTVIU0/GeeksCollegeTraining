package com.nlte.makewish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputMsgActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtInputWish;
    private Button mBtnConfirm;
    private Button mBtnCancel;
    public static final int RESULT_CODE = 2;//确认返回结果码
    public static final int CANCEL_CODE = 3;//取消返回结果码
    public static final String WISH_MSG = "wishMsg";//返回结果码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_msg);

        //初始化界面
        initView();

        mBtnConfirm.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }

    private void initView() {
        mEtInputWish = (EditText) findViewById(R.id.et_input_wish);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
        mBtnCancel = (Button) findViewById(R.id.btn_cancel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                String wishMsg = mEtInputWish.getText().toString().trim();
                if (TextUtils.isEmpty(wishMsg)) {
                    Toast.makeText(InputMsgActivity.this, getString(R.string.toast), Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(WISH_MSG, wishMsg);
                    setResult(RESULT_CODE, intent);
                    finish();
                }
                break;
            case R.id.btn_cancel:
                setResult(CANCEL_CODE);
                finish();
                break;
        }
    }
}
