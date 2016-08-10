package com.nlte.notepad.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.nlte.notepad.bean.Schedule;
import com.nlte.notepad.db.ScheduleDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Function：
 *
 * @author NLTE
 * @date 2016/7/9 0009 11:25
 */
public class DbUtil {
    public static final String TABLE_SCHEDULE = "schedule";//表名
    public static final String ID = "id";//id列
    public static final String REMINDTIME = "remindTime";//remindTime列
    public static final String SCHEDULECONTENT = "scheduleContent";//scheduleContent列
    private static ScheduleDbHelper mScheduleDbHelper;

    public static ScheduleDbHelper getScheduleDbHelper(Context context) {
        if (mScheduleDbHelper == null) {
            return new ScheduleDbHelper(context);
        } else {
            return mScheduleDbHelper;
        }
    }

    public static List<Schedule> GetScheduleFromDb(final Context context){
        final List<Schedule> scheduleList = new ArrayList<>();

        new AsyncTask<String, Integer, List<Schedule>>(){

            @Override
            protected List<Schedule> doInBackground(String... params) {

                ScheduleDbHelper scheduleDb = getScheduleDbHelper(context);
                SQLiteDatabase readableDatabase = scheduleDb.getReadableDatabase();

                Cursor scheduleCursor = readableDatabase.query(TABLE_SCHEDULE,
                        null, null, null, null, null, null);

                while (scheduleCursor.moveToNext()) {
                    Schedule schedule = new Schedule();

                    //获取数据库相应数据
                    int id = scheduleCursor.getInt(
                            scheduleCursor.getColumnIndex(ID));
                    int remindTime = scheduleCursor.getInt(
                            scheduleCursor.getColumnIndex(REMINDTIME));
                    String scheduleContent = scheduleCursor.getString(
                            scheduleCursor.getColumnIndex(SCHEDULECONTENT));

                    schedule.setId(id);
                    schedule.setTime(remindTime);
                    schedule.setSchedule(scheduleContent);

                    scheduleList.add(schedule);
                }

                //关闭游标
                scheduleCursor.close();
                //关闭数据库
                scheduleDb.close();

                return scheduleList;
            }
        }.execute("str");

        return scheduleList;
    }
    public static long addScheduleToDb(Context context, int remindTime, String scheduleContent) {
        ScheduleDbHelper scheduleDb = getScheduleDbHelper(context);
        SQLiteDatabase writableDatabase = scheduleDb.getWritableDatabase();

        //将添加的schedule存入数据库
        ContentValues values = new ContentValues();
        values.put(REMINDTIME, remindTime);
        values.put(SCHEDULECONTENT, scheduleContent);

        long id = writableDatabase.insert(TABLE_SCHEDULE, null, values);

        //关闭数据库
        scheduleDb.close();
        return id;
    }
    public static void deleteScheduleFromDb(Context context, int id) {
        ScheduleDbHelper scheduleDb = getScheduleDbHelper(context);
        SQLiteDatabase readableDatabase = scheduleDb.getReadableDatabase();



        //从数据库中华移除item
        readableDatabase.delete(TABLE_SCHEDULE,
                "id = ?", new String[]{id+""});

        //关闭数据库
        scheduleDb.close();
    }

    /*public static void updateScheduleFromDb(Context context, int id) {
        ScheduleDbHelper scheduleDb = getScheduleDbHelper(context);
        SQLiteDatabase readableDatabase1 = scheduleDb.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, 1);
        readableDatabase1.update(TABLE_SCHEDULE,
                values,
                COLUMN_WHENADDTIME + "=?",
                new String[]{whenAddTime});

        scheduleDb.close();
    }*/
}
