package com.romulodusi.makeyourapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ScreenDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Screens.db";

    public static final String SCREEN_TABLE_NAME = "screen";

    public static final String SCREEN_COLUMN_ID = "id";
    public static final String SCREEN_COLUMN_NAME = "name";
    public static final String SCREEN_COLUMN_CONTENT = "xml_content";
    public static final String SCREEN_COLUMN_PROJECT = "proj_id";

    private static final String SQL_CREATE_ENTRIES =
            "create table " + SCREEN_TABLE_NAME
                    + " (" + SCREEN_COLUMN_ID + " integer primary key, "
                    + SCREEN_COLUMN_PROJECT + " integer forward key, "
                    + SCREEN_COLUMN_CONTENT + " text, "
                    + SCREEN_COLUMN_NAME + " text)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SCREEN_TABLE_NAME;


    public ScreenDbHelper(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean insertProject(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCREEN_COLUMN_NAME, name);

        db.insert(SCREEN_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getProject(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from "+ SCREEN_TABLE_NAME +" where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SCREEN_TABLE_NAME);
        return numRows;
    }

    public Integer deleteProject (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SCREEN_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllScreens(Integer proj_id)
    {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + SCREEN_TABLE_NAME
                + " where " + SCREEN_COLUMN_PROJECT + " = " + proj_id, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(SCREEN_TABLE_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
