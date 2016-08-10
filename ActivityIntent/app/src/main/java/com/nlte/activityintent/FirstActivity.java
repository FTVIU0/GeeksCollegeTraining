package com.nlte.activityintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    private EditText mEtInputMsg;
    static final String INPUT_MSG = "inputMsg";//FirstActivity传递数据的Key
    private Button mBtnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //获取控件
        initView();

        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框的文本
                String msg = mEtInputMsg.getText().toString().trim();
                if (!TextUtils.isEmpty(msg)) {
                    Intent SecondActivityIntent = new Intent(FirstActivity.this, SecondActivity.class);
                    SecondActivityIntent.putExtra(INPUT_MSG, msg);
                    startActivity(SecondActivityIntent);
                } else {
                    Toast.makeText(FirstActivity.this, R.string.toast, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        mEtInputMsg = (EditText) findViewById(R.id.et_input_msg);
        mBtnOk = (Button) findViewById(R.id.btn_submit);
    }
}
