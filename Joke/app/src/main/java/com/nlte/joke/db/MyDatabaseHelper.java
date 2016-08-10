package com.nlte.joke.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * NLTE on 2016/8/4 0004 22:09
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "articles.db";//数据库名称
    public static final int DATABASE_VERSION = 1;//数据库版本号
    public static final String TABLE_N_ARTICLE = "n_article"; // 数据表


    private static final String TABLE_ARTICLE = "create table n_article(" +
            "id integer primary key," +
            "post_author text," +
            "post_date text," +
            "post_content text," +
            "post_title text)";


    private Context mContext;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(TABLE_ARTICLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
