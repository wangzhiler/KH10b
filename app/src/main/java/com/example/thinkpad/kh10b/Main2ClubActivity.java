package com.example.thinkpad.kh10b;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2ClubActivity extends Activity {

    private static final String TAG = "Main2ClubActivity";
    private EditText et1_name;
    private EditText et1_date;
    private Button btn_confirm2;

    private String name;
    private String date;

    private String selected_id="a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_club);

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
        btn_confirm2.setOnClickListener(new View.OnClickListener() {
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
                //跳转回MainActivity并刷新
                Intent intent1 = new Intent(Main2ClubActivity.this, MainActivity.class);
                intent1.putExtra("Table_name", StuManageDbContract.ClubsTable.TABLE_NAME);
                startActivity(intent1);
            }
        });
    }

    private void updateInfo(){
        Dao dao = new Dao(getApplicationContext());
        String sql="update "+ StuManageDbContract.ClubsTable.TABLE_NAME
                +" set Name='"+name+"',R_date='"+date+"' where "
                +" _id="+selected_id;
        dao.update(sql);
    }

    private void insertInfo() {
        Dao dao = new Dao(getApplicationContext());
        String table=StuManageDbContract.ClubsTable.TABLE_NAME;
        ContentValues values = new ContentValues();
        values.put(StuManageDbContract.ClubsTable.COLUMN_NAME_CLUB_NAME,name);
        values.put(StuManageDbContract.ClubsTable.COLUMN_NAME_R_DATE,date);
        dao.insert(table,values);
    }

    private void Info() {
        name=et1_name.getText().toString();
        date=et1_date.getText().toString();
    }

    private void initView() {
        et1_name = (EditText) findViewById(R.id.et2_name);
        et1_date = (EditText) findViewById(R.id.et2_date);
        btn_confirm2 = (Button) findViewById(R.id.btn_confirm2);
    }
}

