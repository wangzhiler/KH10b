package com.example.thinkpad.kh10b;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thinkpad on 2018/2/22.
 */

public class StuManagerDbHelper extends SQLiteOpenHelper {

    private static final String TAG="StuManagerDbHelper";

    //创建Table_Students
    private static final String CREATE_STUDENTSTBL=
            "create table "+StuManageDbContract.StudentsTable.TABLE_NAME +" ("
            + StuManageDbContract.StudentsTable._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StuManageDbContract.StudentsTable.COLUMN_NAME_STUDENT_NAME+" TEXT, "
            + StuManageDbContract.StudentsTable.COLUMN_NAME_STUDENT_No+" TEXT, "
            + StuManageDbContract.StudentsTable.COLUMN_NAME_R_DATE+" TEXT )";
    //创建Table_Clubd
    private static final String CREATE_CLUBSTBL=
            "create table "+ StuManageDbContract.ClubsTable.TABLE_NAME+" ("
            + StuManageDbContract.ClubsTable._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StuManageDbContract.ClubsTable.COLUMN_NAME_CLUB_NAME+"  TEXT, "
            + StuManageDbContract.ClubsTable.COLUMN_NAME_R_DATE+"  TEXT ) ";
    //创建Table_Stu_Club
    private static final String CREATE_STUCLUBTBL=
            "create table "+StuManageDbContract.StuClubTable.TABLE_NAME+" ("
            + StuManageDbContract.StuClubTable._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StuManageDbContract.StuClubTable.COLUMN_NAME_S_RID+" INTEGER, "
            + StuManageDbContract.StuClubTable.COLUMN_NAME_C_RID+" INTEGER, "
            + StuManageDbContract.StuClubTable.COLUMN_NAME_R_DATE+ " TEXT , "
            + " foreign key (" + StuManageDbContract.StuClubTable.COLUMN_NAME_S_RID+
                    ") references  " + StuManageDbContract.StudentsTable.TABLE_NAME+
                    " (" + StuManageDbContract.StudentsTable._ID+
                    "), "
            + " foreign key (" + StuManageDbContract.StuClubTable.COLUMN_NAME_C_RID+
                    ") references  " + StuManageDbContract.ClubsTable.TABLE_NAME+
                    " (" + StuManageDbContract.ClubsTable._ID+
                    ")  )";



    //删除Table_Students
    private static final String SQL_DELETE_STUDENTSTBL=
            "drop table if exists "+ StuManageDbContract.StudentsTable.TABLE_NAME;

    //删除Table_Clubs
    private static final String SQL_DELETE_CLUBSTBL=
            "drop table if exists "+ StuManageDbContract.ClubsTable.TABLE_NAME;

    //删除Table_Stu_Club
    private static final String SQL_DELETE_STUCLUBTBL=
            "drop table if exists "+ StuManageDbContract.StuClubTable.TABLE_NAME;

    public StuManagerDbHelper(Context context) {
        super(context, StuManageDbContract.DB_NAME, null, StuManageDbContract.VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_STUDENTSTBL);
        sqLiteDatabase.execSQL(CREATE_CLUBSTBL);
        sqLiteDatabase.execSQL(CREATE_STUCLUBTBL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_STUDENTSTBL);
        sqLiteDatabase.execSQL(SQL_DELETE_CLUBSTBL);
        sqLiteDatabase.execSQL(SQL_DELETE_STUCLUBTBL);
        onCreate(sqLiteDatabase);
    }
}
