package com.example.thinkpad.kh10b;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2StudentActivity extends Activity {

    private static final String TAG = "Main2StudentActivity";
    private EditText et1_id;
    private EditText et1_name;
    private EditText et1_stuno;
    private EditText et1_date;
    private Button btn_confirm1;

    private String id;
    private String name;
    private String stuno;
    private String date;

    private String selected_id="a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_student);

        Intent intent=getIntent();
        if (intent != null) {
            selected_id=intent.getStringExtra("selected_id");
            Log.d(TAG, "selected_id=="+selected_id);
        } else{
            Log.d(TAG, "intent is null");
        }
        initView();
        initListener();
    }

    private void initListener() {
        btn_confirm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected_id.equals(-1 + "")) {
                    Log.d(TAG, "selectedid=-1");
                    //点击时获得所有text
                    Info();
                    //将数据添加到数据库
                    insertInfo();
                } else{
                    Info();
                    updateInfo();
                }
                //跳转回MainActivity
                Intent intent1 = new Intent(Main2StudentActivity.this, MainActivity.class);
                intent1.putExtra("Table_name", StuManageDbContract.StudentsTable.TABLE_NAME);
                startActivity(intent1);
            }
        });
    }

    private void updateInfo() {
        Dao dao = new Dao(getApplication());
        String sql = "update "+ StuManageDbContract.StudentsTable.TABLE_NAME
                +" set Name='"+name+"',Student_No='"+stuno+"',R_date='"+date+"' where "
                +" _id="+selected_id;
        dao.update(sql);
    }

    private void insertInfo() {
        Dao dao = new Dao(getApplicationContext());
        String table=StuManageDbContract.StudentsTable.TABLE_NAME;
        ContentValues values = new ContentValues();
        values.put(StuManageDbContract.StudentsTable.COLUMN_NAME_STUDENT_NAME,name);
        values.put(StuManageDbContract.StudentsTable.COLUMN_NAME_STUDENT_No,stuno);
        values.put(StuManageDbContract.StudentsTable.COLUMN_NAME_R_DATE,date);
        dao.insert(table,values);
    }

    private void Info() {
        name=et1_name.getText().toString();
        stuno=et1_stuno.getText().toString();
        date=et1_date.getText().toString();
    }

    private void initView() {
        et1_name = (EditText) findViewById(R.id.et1_name);
        et1_stuno = (EditText) findViewById(R.id.et1_stuno);
        et1_date = (EditText) findViewById(R.id.et1_date);
        btn_confirm1 = (Button) findViewById(R.id.btn_confirm1);
    }
}
