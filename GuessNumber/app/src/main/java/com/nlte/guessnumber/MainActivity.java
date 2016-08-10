package com.nlte.guessnumber;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText mEtInputNum;
    private TextView mTvAnswer;
    private int mAnswerNum = 0;//默认随机数
    private Button mBtnSubmit;
    private Button mBtnChangeNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取随机数
        mAnswerNum = (int) (Math.random() * 100);

        //获取控件
        initView();

        //给控件添加点击事件
        mBtnSubmit.setOnClickListener(this);
        mBtnChangeNum.setOnClickListener(this);

    }

    //获取控件
    private void initView() {
        mEtInputNum = (EditText) findViewById(R.id.et_input_number);
        mTvAnswer = (TextView) findViewById(R.id.tv_answer);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mBtnChangeNum = (Button) findViewById(R.id.btn_change_number);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (!TextUtils.isEmpty(mEtInputNum.getText().toString())) {
                    //获取输入的整数
                    int inputNum = Integer.valueOf(mEtInputNum.getText().toString());
                    //对输入的整数与随机数进行比较

                    if (inputNum >= 0 && inputNum < 100) {
                        if (inputNum == mAnswerNum) {
                            mTvAnswer.setText(R.string.answer_correct);
                            mTvAnswer.setTextColor(ContextCompat.getColor(this, R.color.green));
                        } else if (inputNum > mAnswerNum) {
                            mTvAnswer.setText(R.string.answer_big);
                            mTvAnswer.setTextColor(ContextCompat.getColor(this, R.color.red));
                        } else {
                            mTvAnswer.setText(R.string.answer_small);
                            mTvAnswer.setTextColor(ContextCompat.getColor(this, R.color.red));
                        }
                    }
                }else {
                    Toast.makeText(MainActivity.this, R.string.toast, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_change_number:
                mAnswerNum = (int) (Math.random() * 100);
                break;
            default:
                break;
        }
    }
}
