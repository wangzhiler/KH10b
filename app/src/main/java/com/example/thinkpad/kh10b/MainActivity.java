package com.example.thinkpad.kh10b;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private static String strCurrentTable= StuManageDbContract.StudentsTable.TABLE_NAME;
    private ListView listView;
    private SimpleCursorAdapter simpleCursorAdapter;
    private Dao dao;
    private Cursor cursor;

    private List<Map<String,Object>> list;

    private List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
    private TextView tvSelectTable;
    private ListView menu1ListView;
    private String[] from;
    private int[] to;
    private String[] from2;
    private int[] to2;
    private Button btn_insert;
    private Button btn_delete;
    private Button btn_query;
    private Button btn_update;
    private Intent intent;

    private String selected_id="bnm";
    private EditText et_query_by_id;
    private Button btn_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

        //先选择一张表
        selectTable();

        Intent getintent=getIntent();
        if (getintent != null) {
            Log.d(TAG, "aaa....");
            if (strCurrentTable.equals(StuManageDbContract.StudentsTable.TABLE_NAME)) {
                tvSelectTable.setText(StuManageDbContract.StudentsTable.TABLE_NAME);
                showStudentsTable();
                Log.d(TAG, "getintent studenttablefreash");
            } else if (strCurrentTable.equals(StuManageDbContract.ClubsTable.TABLE_NAME)) {
                tvSelectTable.setText(StuManageDbContract.ClubsTable.TABLE_NAME);
                showClubsTable();
                Log.d(TAG, "getintent Clubtablefreash");
            } else if (strCurrentTable.equals(StuManageDbContract.StuClubTable.TABLE_NAME)) {
                tvSelectTable.setText(StuManageDbContract.StuClubTable.TABLE_NAME);
                showStuClubTable();
                Log.d(TAG, "getintent stuClubtablefreash");
            }
        }
    }

    private void testListView() {
        list=new ArrayList<Map<String,Object>>();
        Map map=new HashMap<String,Object>();
        map.put("name","中国");
        map.put("id", 1);
        map.put("no",6789);
        map.put("date", "1990-01-01");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("name","中国");
        map.put("id", 2);
        map.put("no",6789);
        map.put("date", "1990-01-01");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("name","中国");
        map.put("id", 2);
        map.put("no",6789);
        map.put("date", "1990-01-01");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("name","中国");
        map.put("id", 3);
        map.put("no",6789);
        map.put("date", "1990-01-01");
        list.add(map);

        String [] from={"id","name","no","date"};
        int[] to = {R.id.studenttable_S_Rid, R.id.studenttable_Name, R.id.studenttable_Student_No, R.id.studenttable_R_date};

        SimpleAdapter qwqw = new SimpleAdapter(
                this, list, R.layout.item_listview_student_table,
                from, to
        );
        listView.setAdapter(qwqw);
    }

    private void selectTable() {
        //选择一张表，单选框
        final String [] str={StuManageDbContract.StudentsTable.TABLE_NAME
                , StuManageDbContract.ClubsTable.TABLE_NAME, StuManageDbContract.StuClubTable.TABLE_NAME};
        tvSelectTable=(TextView) findViewById(R.id.tv_select_table);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        tvSelectTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("选择Table");
                builder.setSingleChoiceItems(str, -1,//默认选中第一个
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tvSelectTable.setText(str[i]);
                                showTable(str[i]);
                                strCurrentTable = str[i];
                                Log.d(TAG, strCurrentTable);
                            }
                        });
                builder.create().show();
            }
        });
    }

    private void initListener() {

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTable(strCurrentTable);
            }
        });

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_id=et_query_by_id.getText().toString();
                cursor=dao.query2(strCurrentTable,text_id);
                if (cursor != null) {
                    if (strCurrentTable.equals(StuManageDbContract.StudentsTable.TABLE_NAME)) {
                        StudentTableRefreshMenu();
                        StudentTableRefresh(cursor);
                    } else if (strCurrentTable.equals(StuManageDbContract.ClubsTable.TABLE_NAME)) {
                        ClubTableRefreshMenu();
                        ClubTableRefresh(cursor);
                    } else if (strCurrentTable.equals(StuManageDbContract.StuClubTable.TABLE_NAME)) {
                        StuClubTableRefreshMenu();
                        StuClubTableRefresh(cursor);
                    }
                }else{
                    Log.d(TAG, "cursor is null......");}
            }
        });

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "before intent1 at insert");
                String str=tvSelectTable.getText().toString();
                if (str.equals(StuManageDbContract.StudentsTable.TABLE_NAME)) {
                    Log.d(TAG, "before intent2 at insert");
                    intent = new Intent(MainActivity.this, Main2StudentActivity.class);
                    intent.putExtra("selected_id", -1+"");
                    startActivity(intent);
                } else if (str.equals(StuManageDbContract.ClubsTable.TABLE_NAME)) {
                    intent = new Intent(MainActivity.this, Main2ClubActivity.class);
                    intent.putExtra("selected_id", -1+"");
                    startActivity(intent);
                } else if (str.equals(StuManageDbContract.StuClubTable.TABLE_NAME)) {
                    intent = new Intent(MainActivity.this, Main2StuClubActivity.class);
                    intent.putExtra("selected_id", -1+"");
                    startActivity(intent);
                } else{
                    Log.d(TAG, "没跳转");
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(TAG, "id==" + id);
                selected_id=id+"";
                Log.d(TAG, selected_id + "");
            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected_id == "bnm") {
                    Log.d(TAG,"没选中任何listView item");
                    Toast.makeText(MainActivity.this, "必须选中item", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d(TAG, "before intent1 at update");
                    if (strCurrentTable.equals(StuManageDbContract.StudentsTable.TABLE_NAME)) {
                        Log.d(TAG, "before intent2 at update");
                        intent = new Intent(MainActivity.this, Main2StudentActivity.class);
                        intent.putExtra("selected_id",selected_id);
                        startActivity(intent);
                    } else if (strCurrentTable.equals(StuManageDbContract.ClubsTable.TABLE_NAME)) {
                        intent = new Intent(MainActivity.this, Main2ClubActivity.class);
                        intent.putExtra("selected_id",selected_id);
                        startActivity(intent);
                    } else if (strCurrentTable.equals(StuManageDbContract.StuClubTable.TABLE_NAME)) {
                        intent = new Intent(MainActivity.this, Main2StuClubActivity.class);
                        intent.putExtra("selected_id",selected_id);
                        startActivity(intent);
                    } else{
                        Log.d(TAG, "没跳转");
                    }

                    Log.d(TAG, selected_id+"");

                    dao = new Dao(getApplicationContext());
                    String table=strCurrentTable;
                    ContentValues values = new ContentValues();
                    values.put(StuManageDbContract.StudentsTable.COLUMN_NAME_STUDENT_NAME,"rtyuio");
                    String str=tvSelectTable.getText().toString();
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected_id == "bnm") {
                    Log.d(TAG,"没选中任何listView item");
                    Toast.makeText(MainActivity.this, "必须选中item", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d(TAG, selected_id+"");
                    String sql="delete from "+strCurrentTable+" where _id="+selected_id;
                    dao.delete(sql);
                    if (strCurrentTable.equals(StuManageDbContract.StudentsTable.TABLE_NAME)) {
                        showStudentsTable();
                    } else if (strCurrentTable.equals(StuManageDbContract.ClubsTable.TABLE_NAME)) {
                        showClubsTable();
                    } else if (strCurrentTable.equals(StuManageDbContract.StuClubTable.TABLE_NAME)) {
                        showStuClubTable();
                    } else{
                        Log.d(TAG, "delete之后啥都没show");
                    }
                }
            }
        });
    }


    private void showTable(String str) {
        if (str.equals(StuManageDbContract.StudentsTable.TABLE_NAME)) {
            Log.d(TAG, "Student_Table is choosed");
            showStudentsTable();
        } else if (str.equals(StuManageDbContract.ClubsTable.TABLE_NAME)) {
            Log.d(TAG, "Clubs_Table is choosed");
            showClubsTable();
        } else if(str.equals(StuManageDbContract.StuClubTable.TABLE_NAME)){
            Log.d(TAG, "Stu_Club_Table is choosed");
            showStuClubTable();
        } else{
            Log.d(TAG, "啥都没show");
        }
    }

    private void showStuClubTable() {
        cursor = dao.query(StuManageDbContract.StuClubTable.TABLE_NAME);
        StuClubTableRefresh(cursor);
        StuClubTableRefreshMenu();
    }

    private void StuClubTableRefresh(Cursor cursor) {
        from = new String[]{StuManageDbContract.StuClubTable._ID
                , StuManageDbContract.StuClubTable.COLUMN_NAME_S_RID
                , StuManageDbContract.StuClubTable.COLUMN_NAME_C_RID
                , StuManageDbContract.StuClubTable.COLUMN_NAME_R_DATE};
        to = new int[]{R.id.stuclubtable_Rid, R.id.stuclubtable_S_Rid
                , R.id.stuclubtable_C_Rid, R.id.stuclubtable_R_date};
        dao = new Dao(getApplicationContext());
        if (cursor != null) {
            SimpleCursorAdapter sca=new SimpleCursorAdapter(this,R.layout.item_listview_stu_club_table,cursor, from, to);
            listView.setAdapter(sca);
        }else{
            Log.d(TAG, "cursor is null......");}
    }

    private void StuClubTableRefreshMenu() {
        list=new ArrayList<Map<String,Object>>();
        Map map=new HashMap<String,Object>();
        map.put("srid","S_Rid");
        map.put("id", "Rid");
        map.put("crid","C_Rid");
        map.put("date", "R_date");
        list.add(map);

        from2 = new String[]{"id","srid","crid","date"};
        to2 = new int[]{R.id.menu3_Rid, R.id.menu3_S_Rid, R.id.menu3_C_Rid, R.id.menu3_R_date};
        SimpleAdapter setMenu3 = new SimpleAdapter(this, list, R.layout.item_listview_menu_stu_club_table, from2, to2);
        menu1ListView.setAdapter(setMenu3);
    }

    private void showClubsTable() {
        cursor = dao.query(StuManageDbContract.ClubsTable.TABLE_NAME);
        ClubTableRefresh(cursor);
        ClubTableRefreshMenu();
    }

    private void ClubTableRefresh(Cursor cursor) {
        from = new String[]{StuManageDbContract.ClubsTable._ID
                , StuManageDbContract.ClubsTable.COLUMN_NAME_CLUB_NAME
                , StuManageDbContract.ClubsTable.COLUMN_NAME_R_DATE};
        to = new int[]{R.id.clubtable_C_Rid, R.id.clubtable_Name
                , R.id.clubtable_R_date};

        dao = new Dao(getApplicationContext());
        if (cursor != null) {
            SimpleCursorAdapter sca=new SimpleCursorAdapter(this,R.layout.item_listview_club_table,cursor, from, to);
            listView.setAdapter(sca);
        }else{
            Log.d(TAG, "cursor is null......");}
    }

    private void ClubTableRefreshMenu() {
        list=new ArrayList<Map<String,Object>>();
        Map map=new HashMap<String,Object>();
        map.put("name","Name");
        map.put("id", "C_Rid");
        map.put("date", "R_date");
        list.add(map);

        from2 = new String[]{"id","name","date"};
        to2 = new int[]{R.id.menu2_C_Rid, R.id.menu2_Name, R.id.menu2_date};
        SimpleAdapter setMenu2 = new SimpleAdapter(this, list, R.layout.item_listview_menu_club_table, from2, to2);
        menu1ListView.setAdapter(setMenu2);
    }

    private void showStudentsTable() {
        cursor = dao.query(StuManageDbContract.StudentsTable.TABLE_NAME);
        StudentTableRefresh(cursor);
        StudentTableRefreshMenu();
    }

    private void StudentTableRefresh(Cursor cursor) {
        from = new String[]{StuManageDbContract.StudentsTable._ID
                , StuManageDbContract.StudentsTable.COLUMN_NAME_STUDENT_NAME
                , StuManageDbContract.StudentsTable.COLUMN_NAME_STUDENT_No
                , StuManageDbContract.StudentsTable.COLUMN_NAME_R_DATE};
        to = new int[]{R.id.studenttable_S_Rid, R.id.studenttable_Name
                , R.id.studenttable_Student_No, R.id.studenttable_R_date};
        dao = new Dao(getApplicationContext());
        if (cursor != null) {
            SimpleCursorAdapter sca=new SimpleCursorAdapter(this
                    ,R.layout.item_listview_student_table,cursor, from, to);
            listView.setAdapter(sca);
        }else{
            Log.d(TAG, "cursor is null......");}
    }

    private void StudentTableRefreshMenu() {
        list=new ArrayList<Map<String,Object>>();
        Map map=new HashMap<String,Object>();
        map.put("name","Name");
        map.put("id", "S_Rid");
        map.put("no","Student_No");
        map.put("date", "R_date");
        list.add(map);
        from2 = new String[]{"id","name","no","date"};
        to2 = new int[]{R.id.menu1_S_Rid, R.id.menu1_Name, R.id.menu1_Student_No, R.id.menu1_R_date};
        SimpleAdapter setMenu1 = new SimpleAdapter(this, list
                , R.layout.item_listview_menu_student_table, from2, to2);
        menu1ListView.setAdapter(setMenu1);
    }

    private void initView() {
        dao=new Dao(getApplicationContext());
        listView = (ListView) findViewById(R.id.list_view);
        menu1ListView = (ListView) findViewById(R.id.list_view_menu);
        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_update = (Button) findViewById(R.id.btn_update);
        et_query_by_id = (EditText) findViewById(R.id.et_query_id);
        btn_clear = (Button)findViewById(R.id.btn_clear);
    }

}
