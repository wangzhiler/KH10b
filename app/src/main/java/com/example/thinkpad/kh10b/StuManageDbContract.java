package com.example.thinkpad.kh10b;

import android.provider.BaseColumns;

/**
 * Created by thinkpad on 2018/2/22.
 */

public final class StuManageDbContract {

    public StuManageDbContract(){};
    public static final String DB_NAME = "stumanagedatabase.db";
    public static final int VERSION_CODE=3;

    public static abstract class StudentsTable implements BaseColumns {
        public static final String TABLE_NAME = "Table_Students";
        public static final String _ID="_id";
        public static final String COLUMN_NAME_STUDENT_NAME = "Name";
        public static final String COLUMN_NAME_STUDENT_No = "Student_No";
        public static final String COLUMN_NAME_R_DATE = "R_date";
    }
    public static abstract class ClubsTable implements BaseColumns {
        public static final String TABLE_NAME = "Table_Clubs";
        public static final String _ID="_id";
        public static final String COLUMN_NAME_CLUB_NAME = "Name";
        public static final String COLUMN_NAME_R_DATE = "R_date";
    }
    public static abstract class StuClubTable implements BaseColumns {
        public static final String TABLE_NAME = "Table_Stu_Club";
        public static final String _ID="_id";
        public static final String COLUMN_NAME_S_RID="S_Rid";
        public static final String COLUMN_NAME_C_RID="C_Rid";
        public static final String COLUMN_NAME_R_DATE = "R_date";
    }
}
