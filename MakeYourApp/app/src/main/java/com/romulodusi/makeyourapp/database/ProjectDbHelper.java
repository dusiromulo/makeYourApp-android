package com.romulodusi.makeyourapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ProjectDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Projects.db";

    public static final String PROJECTS_TABLE_NAME = "projects";
    public static final String PROJECTS_COLUMN_ID = "id";
    public static final String PROJECTS_COLUMN_NAME = "name";

    private static final String SQL_CREATE_ENTRIES =
            "create table " + PROJECTS_TABLE_NAME
                    + " (" + PROJECTS_COLUMN_ID + " integer primary key, "
                    + PROJECTS_COLUMN_NAME + " text)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PROJECTS_TABLE_NAME;


    public ProjectDbHelper(Context context)
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
        contentValues.put(PROJECTS_COLUMN_NAME, name);

        db.insert(PROJECTS_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getProject(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select * from "+ PROJECTS_TABLE_NAME +" where id="+id+"", null );
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROJECTS_TABLE_NAME);
        return numRows;
    }

    public Integer deleteProject (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PROJECTS_TABLE_NAME,
                        "id = ? ",
                        new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllProjects()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PROJECTS_TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(PROJECTS_COLUMN_NAME)));
            res.moveToNext();
        }
        res.close();

        return array_list;
    }
}
