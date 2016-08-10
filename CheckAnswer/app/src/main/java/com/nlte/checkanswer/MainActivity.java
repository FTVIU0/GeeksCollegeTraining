package com.nlte.checkanswer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton mRbMan, mRbWoman;
    private CheckBox mCbItem1, mCbItem2, mCbItem3, mCbItem4, mCbItem5, mCbItem6;
    private TextView mTvAnswer;
    private Button mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化布局
        initView();

        //添加听事件
        mBtnSubmit.setOnClickListener(this);
    }

    private void initView() {
        mRbMan = (RadioButton) findViewById(R.id.rb_man);
        mRbWoman = (RadioButton) findViewById(R.id.rb_woman);

        mCbItem1 = (CheckBox) findViewById(R.id.cb_item1);
        mCbItem2 = (CheckBox) findViewById(R.id.cb_item2);
        mCbItem3 = (CheckBox) findViewById(R.id.cb_item3);
        mCbItem4 = (CheckBox) findViewById(R.id.cb_item4);
        mCbItem5 = (CheckBox) findViewById(R.id.cb_item5);
        mCbItem6 = (CheckBox) findViewById(R.id.cb_item6);

        mTvAnswer = (TextView) findViewById(R.id.tv_answer);

        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void onClick(View v) {
        String mSexAnswer = null;//性别选择结果
        if (mRbMan.isChecked()) {
            mSexAnswer = mRbMan.getText().toString();
        } else if (mRbWoman.isChecked()) {
            mSexAnswer = mRbWoman.getText().toString();
        }

        String mulAnswer;//多项选择的结果
        if (mCbItem3.isChecked() && mCbItem6.isChecked()
                && !mCbItem1.isChecked() && !mCbItem2.isChecked()
                && !mCbItem4.isChecked() && !mCbItem5.isChecked()) {
            mulAnswer = getString(R.string.correct);
        } else {
            mulAnswer = getString(R.string.wrong);
        }

        //显示结果
        mTvAnswer.setText(String.format(getString(R.string.answer), mSexAnswer, mulAnswer));
    }
}
