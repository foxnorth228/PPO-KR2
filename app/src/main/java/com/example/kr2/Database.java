package com.example.kr2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Database {

    public static int get(String getName) {
        SQLDatabase sql = MainActivity.sqlbase;
        SQLiteDatabase db = sql.getWritableDatabase();
        Cursor c = db.query(SQLDatabase.table,
                new String[] {SQLDatabase.name, SQLDatabase.score},
                SQLDatabase.name + " = ?",
                new String[] {getName},
                null, null, null, null);
        if(c.moveToFirst()) {
            int scoreIndex = c.getColumnIndex(SQLDatabase.score);
            if(scoreIndex >= 0) {
                return c.getInt(scoreIndex);
            }
        }
        return -1;
    }

    public static int put(String name) {
        SQLDatabase sql = MainActivity.sqlbase;
        SQLiteDatabase db = sql.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SQLDatabase.name, name);
        cv.put(SQLDatabase.score, 0);
        int id = -1;
        try{
            id = (int) db.insertOrThrow(SQLDatabase.table, null, cv);
        } catch (SQLiteConstraintException ignored) {
        }
        return id;
    }

    public static void update(String name, int count) {
        SQLiteDatabase db = MainActivity.sqlbase.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SQLDatabase.name, name);
        cv.put(SQLDatabase.score, count);
        db.update(SQLDatabase.table, cv, SQLDatabase.name + " = ?", new String[]{name});
    }

    public static ArrayList<Player> sort() {
        ArrayList<Player> map = new ArrayList<>();
        SQLiteDatabase db = MainActivity.sqlbase.getWritableDatabase();
        Cursor c = db.query(
                SQLDatabase.table,
                null,
                null,
                null,
                null,
                null,
                SQLDatabase.score + " desc"
        );
        if (c != null) {
            if (c.moveToFirst()) {
                int nameColIndex = c.getColumnIndex(SQLDatabase.name);
                int scoreColIndex = c.getColumnIndex(SQLDatabase.score);
                do {
                    map.add(new Player(
                            c.getString(nameColIndex),
                            c.getInt(scoreColIndex)
                    ));
                } while(c.moveToNext());
            }
        }
        return map;
    }

    public static void deleteSqlTable() {
        SQLiteDatabase db = MainActivity.sqlbase.getWritableDatabase();
        db.delete(SQLDatabase.table, null, null);
    }
}
