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

public class Main2StuClubActivity extends Activity {

    private static final String TAG = "Main2StuClubActivity";
    private EditText et1_id;
    private EditText et1_srid;
    private EditText et1_crid;
    private EditText et1_date;
    private Button btn_confirm3;

    private String id;
    private String srid;
    private String crid;
    private String date;
    private String selected_id="a";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_stu_club);

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
        btn_confirm3.setOnClickListener(new View.OnClickListener() {
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

                //点击时获得所有text
//                Info();
                //将数据添加到数据库
//                insertInfo();
                //跳转回MainActivity并刷新
                Intent intent1 = new Intent(Main2StuClubActivity.this, MainActivity.class);
                intent1.putExtra("Table_name", StuManageDbContract.StudentsTable.TABLE_NAME);
                startActivity(intent1);

            }
        });
    }

    private void updateInfo(){
        Dao dao = new Dao(getApplicationContext());
        String sql="update "+ StuManageDbContract.StuClubTable.TABLE_NAME
                +" set S_Rid='"+srid+"',C_Rid='"+crid+"',R_date='"+date
                +"' where _id="+selected_id;
        dao.update(sql);
    }

    private void insertInfo() {
        Dao dao = new Dao(getApplicationContext());
        String table=StuManageDbContract.StuClubTable.TABLE_NAME;
        ContentValues values = new ContentValues();
        values.put(StuManageDbContract.StuClubTable.COLUMN_NAME_S_RID,srid);
        values.put(StuManageDbContract.StuClubTable.COLUMN_NAME_C_RID,crid);
        values.put(StuManageDbContract.StuClubTable.COLUMN_NAME_R_DATE,date);
        dao.insert(table,values);
    }

    private void Info() {
        srid=et1_srid.getText().toString();
        crid=et1_crid.getText().toString();
        date=et1_date.getText().toString();
    }

    private void initView() {
        et1_srid = (EditText) findViewById(R.id.et3_srid);
        et1_crid = (EditText) findViewById(R.id.et3_crid);
        et1_date = (EditText) findViewById(R.id.et3_date);
        btn_confirm3 = (Button) findViewById(R.id.btn_confirm3);
    }
}
