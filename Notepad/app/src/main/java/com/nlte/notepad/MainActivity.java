package com.nlte.notepad;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nlte.notepad.adapter.ScheduleAdapter;
import com.nlte.notepad.bean.Schedule;
import com.nlte.notepad.utils.DbUtil;
import com.nlte.notepad.utils.AlarmUtil;
import com.nlte.notepad.utils.ToastUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_REQUEST_CODE = 1;//Activity请求码
    private ListView mLvSchedule;
    private List<Schedule> mScheduleList;
    private ScheduleAdapter mScheduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLvSchedule = (ListView) findViewById(R.id.lv_schedule);

        /*//启动服务
        startService(new Intent(this, RemindService.class));*/

        //获取数据库中的数据
        mScheduleList = DbUtil.GetScheduleFromDb(this);

        mScheduleAdapter = new ScheduleAdapter(MainActivity.this, mScheduleList);

        mLvSchedule.setAdapter(mScheduleAdapter);

        //添加长按Item事件
        mLvSchedule.setOnItemLongClickListener(lvScheduleItemLongClickListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //获取数据库中的数据
        mScheduleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, RemindService.class));
    }

    private AdapterView.OnItemLongClickListener lvScheduleItemLongClickListener =
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent,
                                               View view, final int position, long id) {

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(getString(R.string.remind))
                            .setMessage(getString(R.string.dialog_message))
                            .setPositiveButton(getString(R.string.ok),
                                    new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    int id = mScheduleList.get(position).getId();

                                    //删除闹钟
                                    PendingIntent pendingIntent = AlarmUtil.getPendingIntent(MainActivity.this, id);
                                    AlarmUtil.deleteAlarm(MainActivity.this, pendingIntent);

                                    //从数据库删除schedule
                                    DbUtil.deleteScheduleFromDb(MainActivity.this, id);


                                    //从listview删除schdule
                                    mScheduleList.remove(position);
                                    mScheduleAdapter.notifyDataSetChanged();//刷新视图
                                    ToastUtil.showMsg(MainActivity.this, getString(R.string.delete_success));
                                }
                            })
                            .setNegativeButton(getString(R.string.cancel), null)
                            .show();

                    return true;
                }
            };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //获取菜单视图
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_schedule:
                Intent intent = new Intent(this, AddScheduleActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REQUEST_CODE
                && resultCode == RESULT_OK) {

            //获取AddScheduleActivity返回的数据
            int remindTime = data.getIntExtra(AddScheduleActivity.REMIND_TIME, 0);
            String scheduleContent = data.getStringExtra(AddScheduleActivity.SCHEDULE_CONTENT);

            //将Schedule存入数据库
            int id = (int) DbUtil.addScheduleToDb(this, remindTime, scheduleContent);

            Schedule scheduleItem = new Schedule();
            scheduleItem.setId(id);
            scheduleItem.setTime(remindTime);
            scheduleItem.setSchedule(scheduleContent);

            mScheduleList.add(scheduleItem);

            //刷新视图
            mScheduleAdapter.notifyDataSetChanged();

            //添加闹钟
            PendingIntent pendingIntent = AlarmUtil.getPendingIntent(MainActivity.this,
                    scheduleContent, id);
            AlarmUtil.addAlarm(MainActivity.this, remindTime,pendingIntent);

        }
    }
}
