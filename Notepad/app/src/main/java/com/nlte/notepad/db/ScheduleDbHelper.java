package com.nlte.notepad.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Function：
 *
 * @author NLTE
 * @date 2016/7/7 0007 22:30
 */
public class ScheduleDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "schedule.db";//数据库名
    private static final int DATABASE_VERSION = 1;//数据库版本号

    public ScheduleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE schedule (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "remindTime INTEGER," +
                "scheduleContent TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
