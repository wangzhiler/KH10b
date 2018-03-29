package com.example.thinkpad.kh10b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by thinkpad on 2018/2/22.
 */

public class Dao {
    private static final String TAG = "Dao";
    private final StuManagerDbHelper stuManagerDbHelper;

    public Dao(Context context) {
        //创建数据库
        stuManagerDbHelper = new StuManagerDbHelper(context);
    }

    //插入
    public void insert(String table, ContentValues values){
        SQLiteDatabase sqLiteDatabase=stuManagerDbHelper.getWritableDatabase();
        //添加数据
        sqLiteDatabase.insert(table, null, values);
        sqLiteDatabase.close();
    }

    public void delete(String sql){
        SQLiteDatabase sqLiteDatabase=stuManagerDbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public void update(String sql){
        SQLiteDatabase sqLiteDatabase=stuManagerDbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public Cursor query2(String table,String id) {
        SQLiteDatabase sqLiteDatabase=stuManagerDbHelper.getReadableDatabase();
        String sql="select * from "+table+" where _id="+id;
        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
        return cursor;
    }

    public Cursor query(String table){
        Log.d(TAG, "query.....//////////////////////////////////");
        SQLiteDatabase sqLiteDatabase=stuManagerDbHelper.getReadableDatabase();
        String sql="select * from "+table;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[0]);
        return cursor;
    }

}
