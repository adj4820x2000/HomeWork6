package com.example.simon.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Store_DB extends SQLiteOpenHelper {

    private static final String database = "store.db";
    private static final int version=1;

    public Store_DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    public Store_DB(Context context){
        this(context,database,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE storeTable(_id integer primary key autoincrement,"+
                "store_name text no null,"+"phone text no null,"+"address text no null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int version) {
        db.execSQL("DROP TABLE IF EXISTS storeTable");
        onCreate(db);
    }
}
