package com.nlte.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nlte.notepad.utils.ToastUtil;

public class AddScheduleActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String REMIND_TIME = "remind_time";//返回定时的时间key
    public static final String SCHEDULE_CONTENT = "schedule_content";//返回的事件key
    private EditText mEtRemindTime;
    private EditText mEtScheduleContent;
    private Button mBtnAdd;
    private String mStrScheduleContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        initView();

        mBtnAdd.setOnClickListener(this);
    }

    /**
     * 初始化界面
     */
    private void initView() {
        mEtRemindTime = (EditText) findViewById(R.id.et_remind_time);
        mEtScheduleContent = (EditText) findViewById(R.id.et_schedule_content);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
    }

    @Override
    public void onClick(View v) {
        String strRemindTime = mEtRemindTime.getText().toString();
        mStrScheduleContent = mEtScheduleContent.getText().toString();

        if (TextUtils.isEmpty(strRemindTime) || TextUtils.isEmpty(mStrScheduleContent)) {
            ToastUtil.showMsg(this, getString(R.string.toast_msg));
        } else {

            int remindTime = Integer.parseInt(strRemindTime);

            if (remindTime >= 24 || remindTime < 0) {
                ToastUtil.showMsg(this, getString(R.string.toast_msg_2));
            } else {

                //将添加的日程数据返回
                Intent intent = new Intent();
                intent.putExtra(REMIND_TIME, remindTime);
                intent.putExtra(SCHEDULE_CONTENT, mStrScheduleContent);
                setResult(RESULT_OK, intent);

                //关闭页面
                finish();
            }
        }
    }
}
