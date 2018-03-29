package com.example.thinkpad.kh10b;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

/**
 * Created by thinkpad on 2018/2/22.
 */

public class TestDatabase extends AndroidTestCase {


    private static final String TAG = "TestDatabase";

    public void testCreate(){
        //测试创建数据库
        Dao dao = new Dao(getContext());

    }

    public void testInsert(){
        //测试添加
        Dao dao = new Dao(getContext());
        String table=StuManageDbContract.StuClubTable.TABLE_NAME;
        ContentValues values = new ContentValues();
//        values.put(StuManageDbContract.StudentsTable.COLUMN_NAME_ENTRY_ID,1);
        values.put(StuManageDbContract.StuClubTable.COLUMN_NAME_S_RID,"tyty");
        values.put(StuManageDbContract.StuClubTable.COLUMN_NAME_C_RID,"bb");
        values.put(StuManageDbContract.StuClubTable.COLUMN_NAME_R_DATE,"2018-01-01");

        dao.insert(table,values);
    }

    public void testDelete(){
        //测试删除语句
        Dao dao = new Dao(getContext());
        String table="";
        String selection="";
        String [] selectionargus=null;
        dao.delete(table,selection,selectionargus);
    }

    public void testUpdate(){
        //测试更新
        Dao dao = new Dao(getContext());
        String table=StuManageDbContract.StudentsTable.TABLE_NAME;
        ContentValues values = new ContentValues();
//        values.put(StuManageDbContract.StudentsTable.COLUMN_NAME_ENTRY_ID,2);
        values.put(StuManageDbContract.StudentsTable.COLUMN_NAME_STUDENT_NAME,"rtyuio");
        dao.update(table,values);
    }

    public void testQuery(){
        //测试查询
        Dao dao = new Dao(getContext());
//        dao.query(StuManageDbContract.StudentsTable.TABLE_NAME);
        Cursor cursor=dao.query(StuManageDbContract.StudentsTable.TABLE_NAME);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex("_id");
            String _id = cursor.getString(index);
            Log.d(TAG, "id==" + _id);
            index = cursor.getColumnIndex("Name");
            String Name = cursor.getString(index);
            Log.d(TAG, "name==" + Name);

        }

        cursor.close();
    }


}
