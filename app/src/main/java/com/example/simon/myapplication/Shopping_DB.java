package com.example.simon.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class Shopping_DB extends SQLiteOpenHelper {
    private static final String database1 = "Shopping.db";
    private static final int version=1;

    public Shopping_DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    public Shopping_DB(Context context){
        this(context,database1,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE itemTable(_id integer primary key autoincrement,"+
                "item_name text no null,"+"item_money text no null,"+"item_describe text no null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int version) {
        db.execSQL("DROP TABLE IF EXIST itemTable");
        onCreate(db);
    }
}
