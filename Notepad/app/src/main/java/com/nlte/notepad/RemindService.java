package com.nlte.notepad;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

import com.nlte.notepad.bean.Schedule;
import com.nlte.notepad.utils.DbUtil;
import com.nlte.notepad.db.ScheduleDbHelper;
import com.nlte.notepad.utils.AlarmUtil;

import java.util.ArrayList;
import java.util.List;

public class RemindService extends Service {

    public RemindService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                List<Schedule> scheduleList = new ArrayList<>();

                getSchedule(scheduleList);

                for (int i = 0; i < scheduleList.size(); i++) {

                        int time = scheduleList.get(i).getTime();

                    //添加闹钟
                    PendingIntent pendingIntent = AlarmUtil.getPendingIntent(getApplicationContext(),
                            scheduleList.get(i).getSchedule(), scheduleList.get(i).getId());
                    AlarmUtil.addAlarm(getApplicationContext(), time,pendingIntent);
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 从数据获取数据
     * @param scheduleList
     */
    private void getSchedule(List<Schedule> scheduleList) {
        ScheduleDbHelper scheduleDb = DbUtil.getScheduleDbHelper(getApplicationContext());
        SQLiteDatabase readableDatabase = scheduleDb.getReadableDatabase();

        Cursor scheduleCursor = readableDatabase.query(DbUtil.TABLE_SCHEDULE,
                null, null, null, null, null, null);

        while (scheduleCursor.moveToNext()) {
            Schedule schedule = new Schedule();

            //获取数据库相应数据
            int id = scheduleCursor.getInt(
                    scheduleCursor.getColumnIndex(DbUtil.ID));
            int remindTime = scheduleCursor.getInt(
                    scheduleCursor.getColumnIndex(DbUtil.REMINDTIME));
            String scheduleContent = scheduleCursor.getString(
                    scheduleCursor.getColumnIndex(DbUtil.SCHEDULECONTENT));

            schedule.setId(id);
            schedule.setTime(remindTime);
            schedule.setSchedule(scheduleContent);

            scheduleList.add(schedule);
        }

        //关闭游标
        scheduleCursor.close();
        //关闭数据库
        scheduleDb.close();
    }
}
