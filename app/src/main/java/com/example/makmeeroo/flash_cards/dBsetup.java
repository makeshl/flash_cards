package com.example.makmeeroo.flash_cards;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.StringDef;

import java.io.InputStream;

/**
 * Created by MakMeeRoo on 11/5/2016.
 */
public class dBsetup extends SQLiteOpenHelper {

    private static final String DBNAME = "Flashcardsdbase";
    private static final int VERSION = 1;

    private static final String TABLENAME = "ListofLessonNames";

    private static final String ID = "ID";
    private static final String LESSONNAME = "LESSONNAME";
    private static final String LESSONLOCATION = "LESSONLOCATION";
    private static final String NOOFLESSONS = "NOOFLESSONS";
    private static final String RATING = "RATING";

    SQLiteDatabase myDB; // Not sure why this is needed?

    public dBsetup(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLENAME + "("+
                ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                LESSONNAME + "TEXT NOT NULL, "+
                LESSONLOCATION + "TEXT NOT NULL, "+
                NOOFLESSONS + "TEXT NOT NULL, "+
                RATING + "TEXT NOT NULL "+ ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDB() {
        myDB = getWritableDatabase();
    }

    public void closeDB() {
        if(myDB != null && myDB.isOpen()) {
            myDB.close();
        }
    }

    public long insert(int id, String lessonname, String lessonlocation, int nooflessons, int rating) {
        ContentValues values = new ContentValues();
        if (id!= -1)
            values.put(ID, id);
        values.put(LESSONNAME, lessonname);
        values.put(LESSONLOCATION, lessonlocation);
        values.put(NOOFLESSONS, nooflessons);
        values.put(RATING, rating);

        return myDB.insert(TABLENAME,null, values);
    }

    public long update(int id, String lessonname, String lessonlocation, int nooflessons, int rating) {
        ContentValues values = new ContentValues();
        values.put(LESSONNAME, lessonname);
        values.put(LESSONLOCATION, lessonlocation);
        values.put(NOOFLESSONS, nooflessons);
        values.put(RATING, rating);
        String where = ID + " = "+ id; // where condition to match on
        return myDB.update(TABLENAME, values, where, null);
    }

    public long delete(int id) {
        String where = ID + " = "+ id; // where condition to match on
        return myDB.delete(TABLENAME, where, null);
    }

}


