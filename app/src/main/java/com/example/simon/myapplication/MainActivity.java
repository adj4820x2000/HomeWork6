package com.example.simon.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText store_name,phone,address;
    ListView store_list;
    Button store_add,store_edit,store_delete,store_query;
    SQLiteDatabase dbrw;
    ArrayAdapter<String> MyArrayAdapter;
    ListView listView = null;
    String tmp_address= "",tmp_storename="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView =new ListView(this);

        store_add = (Button)findViewById(R.id.store_add);
        store_edit = (Button)findViewById(R.id.store_edit);
        store_delete = (Button)findViewById(R.id.store_delete);
        store_query = (Button)findViewById(R.id.store_query);
        store_list = (ListView)findViewById(R.id.store_list);
        store_name = (EditText)findViewById(R.id.store_name);
        phone = (EditText)findViewById(R.id.phone);
        address = (EditText)findViewById(R.id.address);

        String[] items ={"A:地圖","B:商品目錄管理","C:下單管理","D:歷史銷售紀錄"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list,R.id.item,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listltn);

        MyArrayAdapter  = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        store_list.setAdapter(MyArrayAdapter);

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setPositiveButton("OK",null);
        builder.setView(listView);
        final android.support.v7.app.AlertDialog dialog = builder.create();


        Store_DB dbhelper = new Store_DB(this);
        dbrw = dbhelper.getWritableDatabase();
        store_add.setOnClickListener(store_ltn);
        store_edit.setOnClickListener(store_ltn);
        store_delete.setOnClickListener(store_ltn);
        store_query.setOnClickListener(store_ltn);

        String[] colum = {"store_name","phone","address"};
        Cursor c;
        c=dbrw.query("storeTable",colum,null,null,null,null,null);
        store_show(c);


        store_list.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tmp_address = parent.getItemAtPosition(position).toString().split(":|\n")[5];
                tmp_storename= parent.getItemAtPosition(position).toString().split(":|\n")[1];
                dialog.show();
            }
        });
    }
    private ListView.OnItemClickListener listltn = new ListView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ViewGroup vg = (ViewGroup)view;
            TextView txt = (TextView)vg.findViewById(R.id.item);

            switch (parent.getItemAtPosition(position).toString()) {
                case "A:地圖":
                    Intent i = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("LOCATION",tmp_address);
                    i.putExtras(bundle);
                    i.setClass(MainActivity.this,map.class);
                    startActivity(i);
                    break;
                case "B:商品目錄管理":
                    Intent intent = new Intent();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("STORENAME",tmp_storename);
                    intent.putExtras(bundle1);
                    intent.setClass(MainActivity.this,shopping.class);
                    startActivity(intent);
                    break;
                case "C:下單管理":
                    Toast.makeText(view.getContext(),"從選單B:商品目錄管理那邊進入喔",Toast.LENGTH_LONG).show();
                    break;
                case "D:歷史銷售紀錄":
                    Toast.makeText(view.getContext(),"加油",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    Button.OnClickListener store_ltn = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.store_add:
                    newStore();
                    break;
                case R.id.store_edit:
                    renewStore();
                    break;
                case R.id.store_delete:
                    deleteStore();
                    break;
                case R.id.store_query:
                    queryStore();
                    break;
            }
        }
    };
    public void newStore(){
        String[] colum = {"store_name","phone","address"};
        Cursor c;
        String store_all="";
        if(store_name.getText().toString().equals("")||phone.getText().toString().equals("")
                ||address.getText().toString().equals("")){
            Toast.makeText(this,"輸入資料不完全",Toast.LENGTH_SHORT).show();
        }
        else {
            ContentValues cv = new ContentValues();
            cv.put("store_name",store_name.getText().toString());
            cv.put("phone",phone.getText().toString());
            cv.put("address",address.getText().toString());

            dbrw.insert("storeTable",null,cv);

            Toast.makeText(this,"已新增",Toast.LENGTH_SHORT).show();

            c=dbrw.query("storeTable",colum,null,null,null,null,null);
            store_show(c);

            store_name.setText("");
            phone.setText("");
            address.setText("");
        }
    }

    public void renewStore(){
        String[] colum = {"store_name","phone","address"};
        Cursor c;
        String store_all="";
        if(store_name.getText().toString().equals("")||phone.getText().toString().equals("")
                ||address.getText().toString().equals("")){
            Toast.makeText(this,"沒有輸入更新值",Toast.LENGTH_SHORT).show();
        }
        else{
            ContentValues cv =new ContentValues();
            cv.put("phone",phone.getText().toString());
            cv.put("address",address.getText().toString());

            dbrw.update("storeTable",cv,"store_name="+"'"+store_name.getText().toString()+"'",null);
            Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();

            c=dbrw.query("storeTable",colum,null,null,null,null,null);
            store_show(c);

            store_name.setText("");
            phone.setText("");
            address.setText("");
        }
    }

    public void deleteStore(){
        String[] colum = {"store_name","phone","address"};
        Cursor c;
        String store_all="";
        if(store_name.getText().toString().equals("")){
            Toast.makeText(this,"請輸入要刪除的值",Toast.LENGTH_SHORT).show();
        }
        else {
            dbrw.delete("storeTable","store_name="+"'"+store_name.getText().toString()+"'",null);
            Toast.makeText(this,"刪除成功",Toast.LENGTH_SHORT).show();

            c=dbrw.query("storeTable",colum,null,null,null,null,null);
            store_show(c);

            store_name.setText("");
            phone.setText("");
            address.setText("");
        }
    }

    public  void queryStore(){

        String store_all="";
        String[] colum = {"store_name","phone","address"};
        Cursor c;
        if(store_name.getText().toString().equals("")){
            c=dbrw.query("storeTable",colum,null,null,null,null,null);
        }
        else {
            c=dbrw.query("storeTable",colum,"store_name="+"'"+store_name.getText().toString()+"'",null,null,null,null);
        }
        store_show(c);
    }
    public  void store_show(Cursor c){
        String[] colum = {"store_name","phone","address"};
        String store_all="";
        if(c.getCount()>0){
            c.moveToFirst();
            store_all="";
            MyArrayAdapter.clear();
            for(int i=0;i<c.getCount();i++){

                store_all="店名:"+c.getString(0)+"\n"+"電話:"+c.getString(1)+"\n"+"地址:"+c.getString(2);
                c.moveToNext();
                MyArrayAdapter.add(store_all);
                MyArrayAdapter.notifyDataSetChanged();
            }
        }
        else if(c.getCount()==0) {
            MyArrayAdapter.clear();
            MyArrayAdapter.notifyDataSetChanged();
        }
    }
}
