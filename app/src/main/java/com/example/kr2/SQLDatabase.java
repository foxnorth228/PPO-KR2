package com.example.kr2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLDatabase extends SQLiteOpenHelper {
    public static final String databaseName = "players.db";
    public static final int schema = 1;
    public static final String table = "players";
    public static final String id = "id";
    public static final String name = "name";
    public static final String score = "score";

    public SQLDatabase(Context context) {
        super(context, databaseName, null, schema);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table + " ("
                + id + " integer primary key autoincrement, "
                + name + " text unique, "
                + score + " integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + databaseName);
        onCreate(db);
    }
}
