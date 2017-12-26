package com.example.simon.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class shopping extends AppCompatActivity {
    EditText item_name, item_money, item_describe;
    ListView item_list;
    Button item_add, item_edit, item_delete, item_query;
    SQLiteDatabase dbrw2;
    ArrayAdapter<String> MyArrayAdapter1;
    String ch_store_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        item_add = (Button) findViewById(R.id.item_add);
        item_edit = (Button) findViewById(R.id.item_edit);
        item_delete = (Button) findViewById(R.id.item_delete);
        item_query = (Button) findViewById(R.id.item_query);
        item_list = (ListView) findViewById(R.id.item_list);
        item_name = (EditText) findViewById(R.id.item_name);
        item_money = (EditText) findViewById(R.id.item_money);
        item_describe = (EditText) findViewById(R.id.item_describe);



        Intent intent =this.getIntent();
        Bundle bundle = intent.getExtras();
        ch_store_name=bundle.getString("STORENAME");
        Toast.makeText(this,"已進入"+ch_store_name+"的商店囉",Toast.LENGTH_SHORT).show();


        MyArrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        item_list.setAdapter(MyArrayAdapter1);

        Shopping_DB dbhelper = new Shopping_DB(this);
        dbrw2 = dbhelper.getWritableDatabase();
        item_add.setOnClickListener(item_ltn);
        item_edit.setOnClickListener(item_ltn);
        item_delete.setOnClickListener(item_ltn);
        item_query.setOnClickListener(item_ltn);
        item_list.setOnItemClickListener(item_list_ltn);

        String[] colum = {"item_name", "item_money", "item_describe"};
        Cursor c;
        c=dbrw2.query("itemTable",colum,null,null,null,null,null);
        item_show(c);
    }
    ListView.OnItemClickListener item_list_ltn=new ListView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent();
            Bundle b = new Bundle();
            i.setClass(shopping.this,buy.class);
            b.putString("BUYNAME",parent.getItemAtPosition(position).toString().split("\n|:")[1]);
            b.putString("BUYPRICE",parent.getItemAtPosition(position).toString().split("\n|:")[3]);
            i.putExtras(b);
            startActivity(i);
        }
    };

    Button.OnClickListener item_ltn = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_add:
                    newItem();
                    break;
                case R.id.item_edit:
                    renewItem();
                    break;
                case R.id.item_delete:
                    deleteItem();
                    break;
                case R.id.item_query:
                    queryItem();
                    break;
            }
        }
    };

    public void newItem() {
        String[] colum = {"item_name", "item_money", "item_describe"};
        Cursor c;
        String item_all = "";
        if (item_name.getText().toString().equals("") || item_money.getText().toString().equals("")
                || item_describe.getText().toString().equals("")) {
            Toast.makeText(this, "輸入資料不完全", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues cv = new ContentValues();
            cv.put("item_name", item_name.getText().toString());
            cv.put("item_money", item_money.getText().toString());
            cv.put("item_describe", item_describe.getText().toString()+";"+ch_store_name);

            dbrw2.insert("itemTable", null, cv);

            Toast.makeText(this, "已新增", Toast.LENGTH_SHORT).show();

            c = dbrw2.query("itemTable", colum, null, null, null, null, null);
            item_show(c);

            item_name.setText("");
            item_money.setText("");
            item_describe.setText("");
        }
    }

    public void renewItem() {
        String[] colum = {"item_name", "item_money", "item_describe"};
        Cursor c;
        String item_all = "";
        if (item_name.getText().toString().equals("") || item_money.getText().toString().equals("")
                || item_describe.getText().toString().equals("")) {
            Toast.makeText(this, "沒有輸入更新值", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues cv = new ContentValues();
            cv.put("item_money", item_money.getText().toString());
            cv.put("item_describe", item_describe.getText().toString()+";"+ch_store_name);

            dbrw2.update("itemTable", cv, "item_name=" + "'" + item_name.getText().toString() + "'", null);
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();

            c = dbrw2.query("itemTable", colum, null, null, null, null, null);
            item_show(c);

            item_name.setText("");
            item_money.setText("");
            item_describe.setText("");
        }
    }

    public void deleteItem() {
        String[] colum = {"item_name", "item_money", "item_describe"};
        Cursor c;
        String item_all = "";
        if (item_name.getText().toString().equals("")) {
            Toast.makeText(this, "請輸入要刪除的值", Toast.LENGTH_SHORT).show();
        } else {
            dbrw2.delete("itemTable", "item_name=" + "'" + item_name.getText().toString() + "'", null);
            Toast.makeText(this, "刪除成功", Toast.LENGTH_SHORT).show();

            c = dbrw2.query("itemTable", colum, null, null, null, null, null);
            item_show(c);

            item_name.setText("");
            item_money.setText("");
            item_describe.setText("");
        }
    }

    public void queryItem() {
        String[] colum = {"item_name", "item_money", "item_describe"};
        Cursor c;
        String item_all = "";
        if (item_name.getText().toString().equals("")) {
            c = dbrw2.query("itemTable", colum, null, null, null, null, null);
        } else {
            c = dbrw2.query("itemTable", colum, "item_name=" + "'" + item_name.getText().toString() + "'", null, null, null, null);
        }
        item_show(c);
    }
    public void item_show(Cursor c){
        String[] colum = {"item_name", "item_money", "item_describe"};
        String item_all = "";
        if (c.getCount() > 0) {
            c.moveToFirst();
            item_all = "";
            MyArrayAdapter1.clear();
            for (int i = 0; i < c.getCount(); i++) {
                if(c.getString(2).toString().split(";")[1].equals(ch_store_name)) {
                    item_all = "商品:" + c.getString(0) + "\n" + "金額:" + c.getString(1) + "\n" + "商品描述:" + c.getString(2).toString().split(";")[0];

                    MyArrayAdapter1.add(item_all);
                    MyArrayAdapter1.notifyDataSetChanged();
                }
                c.moveToNext();
            }
        }
        else if(c.getCount()==0) {
            MyArrayAdapter1.clear();
            MyArrayAdapter1.notifyDataSetChanged();
        }
    }
}
