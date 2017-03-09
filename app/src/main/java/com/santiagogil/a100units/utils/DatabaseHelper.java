package com.santiagogil.a100units.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database
    private static final String DATABASENAME = "100UnitsDB";
    private static final Integer DATABASEVERSION = 1;

    //Tables
    public static final String TABLEUNITS = "Units";

    //Columns
    public static final String ID = "ID";
    public static final String DESCRIPTION = "Description";
    public static final String USER = "User";
    public static final String COLOR = "Color";


    //Values

    // Create Table Clauses

    private static final String CREATE_TABLE_UNITS = "CREATE TABLE " + TABLEUNITS + " ("
            + ID + " STRING PRIMARY KEY, "
            + DESCRIPTION + " STRING, "
            + COLOR + " STRING, "
            + USER + " STRING "
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE_UNITS);

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
