package com.nlte.joke.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nlte.joke.bean.Article;
import com.nlte.joke.db.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * NLTE on 2016/8/4 0004 21:50
 */
public class FetchDataFromDbTask {
    private Context mContext;

    private MyDatabaseHelper databaseHelper = null;

    public FetchDataFromDbTask(Context context) {
        mContext = context;
    }

    private SQLiteDatabase getSQLiteDatabase() {

        if (databaseHelper == null) {
            databaseHelper = new MyDatabaseHelper(mContext);
        }
        return databaseHelper.getReadableDatabase();
    }

    /**
     * 向数据库插入数据
     *
     * @param articles 数据源
     */
    public void addToDb(List<Article> articles) {

        SQLiteDatabase db = getSQLiteDatabase();

        for (Article item : articles) {
            ContentValues values = new ContentValues();

            values.put("id", item.getID());
            values.put("post_author", item.getPost_author());
            values.put("post_date", item.getPost_date());
            values.put("post_content", item.getPost_content());
            values.put("post_title", item.getPost_title());

            db.insert(MyDatabaseHelper.TABLE_N_ARTICLE, null, values);

            values.clear();
        }
    }


    /**
     * 查询数据库的数据
     *
     * @return 查询的列表
     */
    public List<Article> getDataFromDb() {
        SQLiteDatabase db = getSQLiteDatabase();

        List<Article> articles = new ArrayList<>();

        Cursor cursor = db.query(MyDatabaseHelper.TABLE_N_ARTICLE,
                null, null, null, null, null, "post_date desc");

        while (cursor.moveToNext()) {

            Article item = new Article();

            item.setID(cursor.getInt(cursor.getColumnIndex("id")));
            item.setPost_author(cursor.getString(cursor.getColumnIndex("post_author")));
            item.setPost_date(cursor.getString(cursor.getColumnIndex("post_date")));
            item.setPost_content(cursor.getString(cursor.getColumnIndex("post_content")));
            item.setPost_title(cursor.getString(cursor.getColumnIndex("post_title")));

            articles.add(item);
        }
        cursor.close();//关闭游标，释放资源

        return articles;
    }

    /**
     * 更新数据库数据
     *
     * @param articles 数据源
     */
    public void updateData(List<Article> articles) {
        SQLiteDatabase db = getSQLiteDatabase();

        for (Article item : articles) {

            ContentValues values = new ContentValues();

            values.put("post_date", item.getPost_date());
            values.put("post_content", item.getPost_content());
            values.put("post_title", item.getPost_title());

            db.update(MyDatabaseHelper.TABLE_N_ARTICLE,
                    values,
                    "id = ?",
                    new String[]{String.valueOf(item.getID())});
        }
    }
}
